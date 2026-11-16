import java.io.*;import java.nio.file.*;import java.util.*;
public class ResourceReport {
 public static void main(String[] a)throws IOException {
  List<Path> files=List.of(Path.of("store1.txt"),Path.of("store2.txt"));
  try(BufferedWriter out=Files.newBufferedWriter(Path.of("summary.txt"))){
   for(Path p:files){
    try(BufferedReader in=Files.newBufferedReader(p)){
     long lines=in.lines().count();out.write(p+" : "+lines);out.newLine();
    }catch(IOException ex){System.err.println("Failed file "+p+": "+ex.getMessage());}
   }
  }
 }
}
