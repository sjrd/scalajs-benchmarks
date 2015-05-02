#!/bin/sh

. "$(dirname "$0")/common/benchmark-runner.sh"

EXCLUDE=sudoku

detect_engines $ENGINES

for benchmark in $(find . -mindepth 2 -name "run.sh" | grep -vE "$EXCLUDE"); do
	name="$(basename "$(dirname "$benchmark")")"
	echo
	info "$name [all] sbt"
	TIME="%E" time sbt "$name/fastOptJS" "$name/fullOptJS" "set scalaJSOutputMode in Global := org.scalajs.core.tools.javascript.OutputMode.ECMAScript6" "$name/fastOptJS" "set scalaJSOutputMode in Global := org.scalajs.core.tools.javascript.OutputMode.ECMAScript6StrongMode" "$name/fastOptJS" >/dev/null

	for mode in $MODES; do
		for engine in $ENGINES; do
			run_benchmark_mode "$engine" "$name" "$mode"
		done
	done
done
