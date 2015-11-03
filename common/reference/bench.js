/*
 * Pure JS Benchmark implementation  of org.scalajs.benchmark.Benchmark
 *
 * @author Nicolas Stucki
 */

Benchmark = function(prefix, run, setUp, tearDown) {
  this.prefix = prefix;
  this.minWarmUpTime = 100;
  this.minRunTime = 2000;
  this.run = run;
  if (!setUp)
    setUp = function () {};
  this.setUp = setUp;
  if (!tearDown)
    tearDown = function () {};
  this.tearDown = tearDown;
};

Benchmark.prototype.runBenchmark = function(timeMinimum) {
  var runs = 0;
  var elapsed = 0;
  while (elapsed < timeMinimum) {
    runs++;
    this.setUp();
    var start = new Date();
    this.run();
    var end = new Date();
    this.tearDown();
    elapsed += end - start;
  }
  return {
    avg: 1000 * elapsed / runs,
    runs: runs
  };
}

Benchmark.prototype.warmUp = function() {
  this.runBenchmark(this.minWarmUpTime);
};

Benchmark.prototype.report = function() {
  this.warmUp();
  var result = this.runBenchmark(this.minRunTime);
  console.log(this.prefix + ": " + result.avg + " us (" + result.runs + " runs)");
};
