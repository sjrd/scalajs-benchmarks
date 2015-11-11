// The initial `;` is neccesarry to avoid applying the function definition to the
// last statement of base.js which ends with a function definition without the
// `;` at the end.
;
(function(g) {
  g.performance = performance;
  g.Benchmark = Benchmark;
  g.BenchmarkSuite = BenchmarkSuite;
})((typeof global === "object" && global && global["Object"] === Object) ? global : this);
