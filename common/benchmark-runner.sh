#!/bin/sh
#                     __
#     ________ ___   / /  ___      __ ____  Scala.js Benchmarks
#    / __/ __// _ | / /  / _ | __ / // __/  (c) 2013, Jonas Fonseca
#  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \
# /____/\___/_/ |_/____/_/ | |__/ /____/
#                          |/____/
#

# Run a benchmark against a JavaScript VM.

# set -x

RUN_DIR="$(dirname "$0")"
ROOT_DIR="./$(git rev-parse --show-cdup)"
#ENGINES="d8 node"
ENGINES=iojs
MODES="fastopt fullopt es6 strongmode js"
SEP='
'

trap "exit" SIGHUP SIGINT SIGTERM

die() {
	echo >&2 "$@"
	exit 1
}

info()
{
	printf "%-25s : " "$1"; shift
	test $# -gt 0 && echo "$@"
}

print_option()
{
	echo "[$@]" | sed 's/ /|/g'
}

find_binary()
{
	version_options="$1"; shift
	engine="$1"

	for bin in $@; do
		path="$(which "$bin" 2>/dev/null)"
		if test -n "$path"; then
			info "$engine" "$($path $version_options) [$path]"
			eval "${engine}_bin=$path"
			return
		fi
	done

	info "$engine" "No binary found while searching \$PATH for $@"
}

detect_engine()
{
	engine="$1"

	test -n "$engine_bin" && return

	case "$engine" in
	d8)	find_binary "-e print(version())" d8 ;;
	node)	find_binary "-v" node nodejs js ;;
	iojs)   find_binary "-v" iojs ;;
	phantomjs)
		find_binary "-v" phantomjs ;;
	*)	die "Unknown engine: $engine"
	esac
}

detect_engines()
{
	for engine in $@; do
		detect_engine "$engine"
	done
}

run_benchmark_mode()
{
	engine="$1" benchmark="$2" mode="$3"
	out_dir="$ROOT_DIR/$benchmark/target/scala-2.11"
	lib_dir="$ROOT_DIR/common"
	js="$out_dir/$benchmark.$engine-$mode.js"
	engine_bin=$(eval echo \$"${engine}_bin")

	test -z "$engine_bin" && return

	{
		test -e "$lib_dir/$engine-stubs.js" &&
			cat "$lib_dir/$engine-stubs.js"
		case "$mode" in
		js)		cat "$lib_dir/reference/bench.js" \
				    "$lib_dir/reference/$benchmark.js" ;;
		fullopt)	cat "$out_dir/$benchmark-opt.js" \
				    "$out_dir/$benchmark-launcher.js" ;;
		fastopt)	cat "$out_dir/$benchmark-fastopt.js" \
				    "$out_dir/$benchmark-launcher.js" ;;
		es6)    	cat "$out_dir/$benchmark-es6.js" \
				    "$out_dir/$benchmark-launcher.js" ;;
		strongmode)	cat "$out_dir/$benchmark-strongmode.js" \
				    "$out_dir/$benchmark-launcher.js" ;;
		*)		die "Unknown mode: $mode"
		esac
		cat "$lib_dir/start-benchmark.js"
	} > "$js"

	case "$engine" in
	iojs) engine_opts="--harmony-rest-parameters --strong-mode" ;;
        *)    engine_opts=""
	esac

	info "$benchmark [$mode] $engine"
	# Remove benchmark prefix (e.g. DeltaBlue:) and squelch
	# PhantomJS warning
	"$engine_bin" $engine_opts "$js" 2>&1 | sed 's/[^:]*:\s//' | grep -v phantomjs
}

run_benchmark()
{
	benchmark="$(basename "$(cd "$RUN_DIR" && pwd)")"

	engines=
	modes=

	while test $# != 0; do
		arg="$1"; shift

		case "$arg" in
		fastopt|fullopt|js)
			modes="$modes$SEP$arg" ;;
		d8|node|phantomjs)
			engines="$engines$SEP$arg" ;;
		*)
			die "Usage: $0 $(print_option $ENGINES) $(print_option $MODES)"
		esac
	done

	test -z "$engines" && engines="d8" ||
		engines="$(echo "$engines" | sort -u)"

	test -z "$modes" && modes="fullopt" ||
		modes="$(echo "$modes" | sort -u)"

	detect_engines "$engines"

	for mode in $modes; do
		for engine in $engines; do
			run_benchmark_mode "$engine" "$benchmark" "$mode"
		done
	done
}
