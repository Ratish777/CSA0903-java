import java.io.*;import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
public class PassStore {
    private final Path file;PassStore(Path file){this.file=file;}
    public void add(String passId,String regNo,String name,int route,double amount)throws IOException{try(BufferedWriter w=Files.newBufferedWriter(file,StandardCharsets.UTF_8,StandardOpenOption.CREATE,StandardOpenOption.APPEND)){w.write(String.join(",",passId,regNo,name,String.valueOf(route),String.valueOf(amount)));w.newLine();}}
    public String[] find(String passId)throws IOException{try(BufferedReader r=Files.newBufferedReader(file,StandardCharsets.UTF_8)){String line;while((line=r.readLine())!=null){String[] fields=line.split(",",-1);if(fields.length!=5)continue;if(fields[0].equals(passId))return fields;}}return null;}
    public static void main(String[] args)throws IOException{Path f=Files.createTempFile("passes",".csv");PassStore s=new PassStore(f);s.add("P1001","192311001","Arun",12,4500);Files.writeString(f,"P1002,DAMAGED LINE\n",StandardOpenOption.APPEND);s.add("P1003","192311003","Divya",7,3800);System.out.println(Arrays.toString(s.find("P1003")));System.out.println(Arrays.toString(s.find("P1002")));Files.delete(f);}
}
