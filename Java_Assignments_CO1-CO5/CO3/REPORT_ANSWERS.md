# CO3 – Exceptions and Multithreading (Q1–Q7)

## Q1 – ATM exception analysis
Relevant hierarchy: `Throwable` → `Error` and `Exception`; `Exception` → `RuntimeException` (unchecked) and checked exceptions such as `IOException`. `NumberFormatException` and `IllegalArgumentException` are unchecked; `FileNotFoundException`/`IOException` are checked; `OutOfMemoryError` is an Error and normally must not be caught as an ordinary input failure.

| Failure | Type | Classification | Handling |
|---|---|---|---|
| Letters entered for numeric amount | NumberFormatException | unchecked | catch input/format error |
| Negative withdrawal | IllegalArgumentException or domain exception | unchecked unless custom checked | reject before debit |
| Amount exceeds balance | InsufficientFundsException (custom) | checked if extends Exception | catch business failure |
| Missing card-reader file | FileNotFoundException | checked | catch IOException family |
| JVM memory exhaustion | OutOfMemoryError | Error | not a normal recoverable ATM condition |

Catch specific exceptions before broader `Exception`; otherwise the broad catch makes later specific catches unreachable. `finally` is used for cleanup such as closing resources and runs on normal and exceptional completion (except abrupt JVM termination). One catch of `Exception` is acceptable only at a final application boundary for logging/user-safe fallback; it loses precise recovery and diagnostic handling.

Alternative: validate with `Scanner.hasNextInt()` and return error codes. This avoids stack-trace construction for routine invalid input but requires explicit checks at every boundary. Both approaches are O(1) for a single withdrawal; exception construction consumes extra temporary memory for the exception and stack trace.

## Q2 – Online examination exception propagation
Use checked `ExamException extends Exception`; domain subclasses such as `ExamWindowClosedException`, `StudentNotRegisteredException`, `SubmissionStorageException`. Keep low-level `IOException` as the cause of `SubmissionStorageException`, not as the UI contract. Flow: Storage → throws SubmissionStorageException(cause IOException) → Service propagates/handles → Controller displays a safe message and logs the cause. If cause is omitted, `getCause()` is null and the original disk/quota reason is lost. Checked exceptions force callers to catch/declare; unchecked exceptions reduce signature noise but do not force recovery. The supplied TODO is completed in `Q2/StorageDemo.java`.

## Q3 – Resource safety
Manual `close()` in a finally block can itself fail and can be skipped if cleanup is incorrectly structured; multiple resources require nested cleanup. Try-with-resources closes resources in reverse declaration order, even when the body throws. If both body and close fail, the body exception is primary and close failure is suppressed (`getSuppressed()`). It is not sufficient when writes are not atomic, data is corrupt, disk is full, retries/idempotency are absent, or errors are swallowed. For f files, processing time is O(total bytes); open one input at a time to cap handles at O(1). Opening all f simultaneously uses O(f) handles and risks exhaustion.

## Q4 – Thread lifecycle
`Thread` subclass combines task and execution object and prevents extending another class; `Runnable` separates task from thread and is generally preferred. `start()` schedules a new thread; calling `run()` directly is an ordinary method call on the current thread. States: NEW → RUNNABLE → (BLOCKED/WAITING/TIMED_WAITING) → TERMINATED. `sleep()` pauses current thread; `join()` waits for completion; `isAlive()` checks liveness. Completion order is scheduler-dependent, so correctness must not depend on print order. A fixed `ExecutorService` bounds concurrent workers; n tasks on p workers require approximately n/p waves, plus scheduling and I/O costs, and O(p) worker stacks.

## Q5 – Cinema seat race
The critical section is check-free-then-mark-booked-and-increment-count. Without synchronization two threads can both observe false and sell the same seat. `synchronized` makes the operation atomic under the object's intrinsic monitor. The completed `SeatBooking.java` uses a synchronized method and produces `Seats sold: 10`. A synchronized block can narrow the lock to the shared state. `AtomicBoolean.compareAndSet(false,true)` is a lock-free per-seat alternative; it uses O(s) booleans/references and each attempt is O(1) expected, with retries under contention. For 20 screens use one lock/state per screen or per-seat atomic flags, not one global lock.

## Q6 – Deadlock and producer/consumer
Deadlock requires mutual exclusion, hold-and-wait, no pre-emption and circular wait. Prevent circular wait by locking accounts in a globally consistent order (e.g. ascending account ID). In the bounded token buffer, `put` waits in a `while(count==capacity)` loop; `take` waits in `while(count==0)`. The loop rechecks the condition after wakeups/spurious wakeups. `wait()` releases the monitor; `sleep()` does not. `notifyAll()` wakes all waiters so eligible threads can recheck conditions. Completed `TokenCounter.java` uses a capacity-five circular buffer and outputs total 210. `ArrayBlockingQueue<Integer>(5)` is a safer library alternative; `put/take` are O(1) queue operations and storage O(5).

## Q7 – Concurrent result processor design
Use `ExecutorService` with a fixed pool (start with available processors, then benchmark 1, 2, 4, 8), submit one `Callable<Result>` per file and retain `Future<Result>`. Each callable reads and validates one file, returning success or a structured failure result; catch per-file exceptions so one corrupt file does not stop other tasks. Aggregate futures after completion; use `ConcurrentLinkedQueue` or thread-safe counters for shared results. Pseudocode: list files; create pool(p); submit callable for each file; for each Future get result and increment success/failure; shutdown; await termination; print totals. For n files, ideal CPU-bound time approaches O(total work/p), but I/O and scheduling limit scaling; memory O(n) futures/results plus O(p) thread stacks. One-thread-per-file creates excessive stacks; parallel streams offer less explicit failure and pool control. Record actual timings locally; do not fabricate them.
