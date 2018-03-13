
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class Ej2 {
    static String command;
    static String filename;
    public static void main (String[] args){
        if (args.length != 2)
            return; //error, exception, fin

        command = args[0];
        filename = args[1];

        if (command.equals("init")){
            Ej2.init(filename);
        }else if (command.equals("fin")){
            Ej2.fin(filename);
        }else{
            System.err.println("Error: "+ command + " command not recognized");
            return; //error, exception, fin
        }
    }

    public static void init(String filename){
        System.out.println("init");
        ArrayList<Integer> pedidos = readFile(filename);
        writePopulationFile(pedidos,100);
        // Leer la info de filename y generar
        // los arhivos necesarios para la poblacion
        // y la configuracion del AE
    }

    public static void fin(String filename){
        System.out.println("fin");
        //Escribir en filename el resultado
        // en el formato especificado.
    }

        public static void writePopulationFile(ArrayList<Integer> pedidos,int tam){

          try{
              PrintWriter writer = new PrintWriter("ec/app/Practico1/population.pop", "UTF-8");

              writer.println("Number of Individuals: i"+tam+"|");

              for (int i = 0;i<tam ;i++ ) {
                java.util.Collections.shuffle(pedidos);

                writer.println("Individual Number: i"+i+"|");
                writer.println("Evaluated: F");
                writer.println("Fitness: d0|0.0|");
                for (Integer pedido : pedidos) {
                  writer.print("i"+pedido+"|");
                }
                writer.println("");
              }
              writer.close();
          } catch (IOException e) {
             e.printStackTrace();
          }

        }

    public static ArrayList<Integer> readFile(String filename)
    {

		BufferedReader br = null;
		FileReader fr = null;
    ArrayList<Integer> result = new ArrayList<Integer>();

    try {

			fr = new FileReader(filename);
			br = new BufferedReader(fr);

			String sCurrentLine;
      int nrLine=1;
      int maxSize;
      int cantPed=-1;
			while ((sCurrentLine = br.readLine()) != null) {
        if(nrLine==1){
          cantPed=Integer.valueOf(sCurrentLine.trim());
        }
        if(nrLine==2){
          maxSize=Integer.valueOf(sCurrentLine.trim());
        }

        if(nrLine>2){

          Pattern pattern = Pattern.compile("(\\d\\d*)");
          Matcher matcher = pattern.matcher(sCurrentLine);
          matcher.find();
          int tam = Integer.valueOf(matcher.group(1));
          matcher.find();
          int cant = Integer.valueOf(matcher.group(1));

          cantPed--;

          for (int i = 0;i<cant ;i++ ) {
            result.add(tam);
          }
          //System.out.println(tam+" - "+cant);
        }

				nrLine++;
			}
      if(cantPed!=0){
        System.err.println("Error: Formato invalido");
      }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    return result;
	}
}
