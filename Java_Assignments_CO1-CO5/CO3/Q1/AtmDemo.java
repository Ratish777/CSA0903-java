import java.io.*;
import java.util.*;
class InsufficientFundsException extends Exception { InsufficientFundsException(String m){super(m);} }
public class AtmDemo {
 static void withdraw(String account,double amount,double balance,File cardFile)throws IOException,InsufficientFundsException {
  if(account==null||account.isBlank()) throw new IllegalArgumentException("Account required");
  if(amount<=0) throw new IllegalArgumentException("Amount must be positive");
  try(FileInputStream in=new FileInputStream(cardFile)){ in.read(); }
  if(amount>balance) throw new InsufficientFundsException("Insufficient balance");
  System.out.println("Dispensed Rs "+amount);
 }
 public static void main(String[] args){
  try { withdraw("A100",500,1000,new File("card.dat")); }
  catch(InsufficientFundsException e){System.out.println(e.getMessage());}
  catch(IllegalArgumentException e){System.out.println("Invalid input: "+e.getMessage());}
  catch(IOException e){System.out.println("Card reader error: "+e.getMessage());}
  finally {System.out.println("Transaction attempt finished");}
 }
}
