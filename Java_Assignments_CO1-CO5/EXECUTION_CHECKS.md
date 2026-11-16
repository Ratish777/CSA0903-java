# Local compile and execution checks performed in this workspace

All `.java` files in the pack were individually compiled with the installed `javac` command. The following console programs were run successfully in this environment:

| Program | Observed output summary |
|---|---|
| CO1 Task03 ParkingFacility | Allocations `[0,0]`, `[0,1]`, `[1,0]`, then `null`; release `true false false`; reallocation `[0,1]` |
| CO1 Task08 FeeDemo | TwoWheeler Rs 30.0; Car Rs 90.0; ElectricCar Rs 210.0; equality `true` |
| CO2 Q3 Attendance | `Unique students: 2` |
| CO3 Q2 StorageDemo | Saved answer01.pdf; wrapped storage error and cause `Disk quota exceeded` |
| CO3 Q5 SeatBooking | `Seats sold: 10` |
| CO3 Q6 TokenCounter | `Sum of tokens collected: 210 (expected 210)` |
| CO5 TaskB1 PassStore | Valid P1003 record returned; malformed P1002 line ignored (`null`) |

These are command-line checks performed in the model workspace, not screenshots from the student's IntelliJ installation. CO4 GUI programs and MySQL/JDBC operations require the student's desktop/database environment and were not represented as executed here. CO5 PassLoader is a JDBC method and intentionally has no standalone `main` method; it requires a configured database connection.
