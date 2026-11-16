import java.util.concurrent.*;
public class ScannerStations {
 public static void main(String[] args)throws InterruptedException {
  ExecutorService pool=Executors.newFixedThreadPool(4);
  try { for(int i=1;i<=4;i++){final int station=i;pool.submit(()->{try{Thread.sleep(100L*station);}catch(InterruptedException e){Thread.currentThread().interrupt();}System.out.println("Station "+station+" scanned 25 scripts");});} }
  finally {pool.shutdown();pool.awaitTermination(1,TimeUnit.MINUTES);}
  System.out.println("All stations completed");
 }
}
