# CO2 – Strings and Collections

## Q1 – String, StringBuilder, StringBuffer
`String` is immutable; repeated `+` inside a loop creates intermediate objects and can approach quadratic copying in total characters. `StringBuilder` is mutable and fastest for one-thread local ticket construction. `StringBuffer` synchronizes individual methods and is appropriate only when a mutable buffer is genuinely shared between threads; it does not make a multi-step log operation automatically atomic. Recommend StringBuilder per ticket and a thread-safe logging facility/lock for shared log writes. `String.join`/`StringJoiner` are concise for known fields; `String.format` is readable but generally has formatting overhead. Benchmark short and large tickets over repeated runs with `System.nanoTime`; report measured values only from your machine.

## Q2 – ArrayList, LinkedList, ArrayDeque
ArrayList append amortized O(1), indexed access O(1), middle/front insertion/removal O(n) due to shifting, iteration O(n), compact array storage. LinkedList insertion/removal at a known node O(1), but locating index O(n), indexed access O(n), iteration O(n), higher per-element node memory. ArrayDeque add/remove at both ends amortized O(1), no indexed access; best for an Up Next double-ended queue. For a read/index-heavy playlist, ArrayList is reasonable; reconsider when operations concentrate at both ends. Plain array with manual shift has O(n) insert/remove and O(1) indexed access.

## Q3 – equals/hashCode
`equals` defines equality by registration number, so `hashCode` must derive from the same immutable field. Missing hashCode can place equal students in different buckets and permit duplicates. The corrected code is `@Override public int hashCode(){ return Objects.hash(regNo); }`. Never mutate a key field after insertion into a hash collection: the object may be in a bucket inconsistent with its new hash. TreeSet with comparator is O(log n); HashMap keyed by regNo and HashSet average O(1); all require O(n) storage.

## Q4 – Pharmacy maps
Use HashMap for fastest average code lookup (O(1)); TreeMap when sorted-by-code iteration is required (O(log n) lookup/insert); LinkedHashMap when insertion order must be preserved (average O(1)). A practical design may maintain a primary HashMap plus sorted report output, or TreeMap if ordered output is frequent. Do not depend on HashMap iteration order.

## Q5 – Generic ranking
Use `Comparator.comparingInt(points).reversed().thenComparing(goalDiff reversed).thenComparing(name)`; comparator chain gives deterministic ranking and avoids subtraction overflow. `Leaderboard<T>` accepts `Comparator<? super T>`, stores items and returns a sorted copy so internal insertion order is not changed. `List.sort` is O(n log n), copy uses O(n) additional space. Comparable embeds one natural order in a type; Comparator supports multiple reusable rankings. Expected output: `[Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]`; Bisons precedes Panthers alphabetically after equal points and goal difference.

## Q6 – Emergency triage
A FIFO queue cannot prioritise severity. PriorityQueue comparator orders severity ascending (1 first), then arrival sequence ascending for FIFO ties. `offer`/`poll` O(log n), peek O(1), storage O(n). Iterating/printing the heap does not guarantee sorted treatment order; repeatedly poll a copy to display order. A continuously arriving stream of critical cases can starve severity 5; use aging (reduce effective severity as waiting time crosses thresholds) or reserved capacity. For five severity levels, five FIFO queues allow O(1) enqueue and O(5)=O(1) selection; memory O(n). A sorted LinkedList insertion costs O(n).

## Q7 – Design challenge
Use Q2 playlist as the redesign case: observed indexed reads and full iteration favour ArrayList; frequent additions/removals at both ends favour ArrayDeque for the Up Next queue. Separate playlist history from the operational queue rather than forcing one collection to serve incompatible access patterns. State a threshold from actual benchmark results before recommending a change. A robust redesign uses ArrayList for browsable playlist and ArrayDeque for the next-up deque, with IDs to avoid relying on mutable object equality. Include your own benchmark table (sizes, operation, runs, average ns) and test table; do not invent timing values.
