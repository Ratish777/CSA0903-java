import java.nio.file.*;import java.util.*;import java.util.concurrent.*;
public class ResultProcessor {
 record Outcome(Path file, boolean success, String message){}
 static Outcome process(Path p){try{String s=Files.readString(p).trim();int score=Integer.parseInt(s);if(score<0||score>100)throw new IllegalArgumentException("score outside 0..100");return new Outcome(p,true,"score="+score);}catch(Exception e){return new Outcome(p,false,e.getClass().getSimpleName()+": "+e.getMessage());}}
 public static void main(String[] args)throws Exception{
  List<Path> files=List.of(Path.of("score1.txt"),Path.of("score2.txt"),Path.of("missing.txt"));
  ExecutorService ex=Executors.newFixedThreadPool(4);List<Future<Outcome>> futures=new ArrayList<>();
  try{for(Path p:files)futures.add(ex.submit(()->process(p)));int ok=0,bad=0;for(Future<Outcome> f:futures){Outcome o=f.get();System.out.println(o.file()+" "+o.message());if(o.success())ok++;else bad++;}System.out.println("Success="+ok+", Failed="+bad);}finally{ex.shutdown();}
 }
}
