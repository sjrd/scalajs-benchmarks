#!/bin/sh

. "$(dirname "$0")/common/benchmark-runner.sh"

EXCLUDE=sudoku

detect_engines $ENGINES

for benchmark in $(find . -mindepth 2 -name "run.sh" | grep -vE "$EXCLUDE"); do
	name="$(basename "$(dirname "$benchmark")")"
	echo
	info "$name [pack] sbt"
	TIME="%E" time sbt "$name/packageJS" >/dev/null
	info "$name [fastopt] sbt"
	TIME="%E" time sbt "$name/fastOptJS" >/dev/null
	info "$name [fullopt] sbt"
	TIME="%E" time sbt "$name/fullOptJS" >/dev/null

	for mode in $MODES; do
		for engine in $ENGINES; do
			run_benchmark_mode "$engine" "$name" "$mode"
		done
	done
done
