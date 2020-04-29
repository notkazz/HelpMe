/*

    Reader Class made to read data.txt files.

*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Reader {
    
    
    
    public void Reader(){
    }
    
    

    /*
     fileReader Method

     It uses bufferedReader to read line by line until the last line.

     Every line it will split the line string every " " and create an array of Strings.

     The "if"s and "else"s will catch the right lines by its patterns,give it the right treatment, and proceed with the while loop.

    */
    public void fileReader2(String fin) throws IOException {
        Decoder decoder = new Decoder();

        Map<String,String> dicionario = new HashMap<String,String>();
        Map<String,Integer> colecaoMetais = new HashMap<String,Integer>();
        BufferedReader br;
        String alien_Sound_1,alien_Sound_2,alien_Sound_3,alien_Sound_4;

        try { 
            br = new BufferedReader(new FileReader(fin));
        
        int numLinhas = 0;
        String line = null;
        while ((line = br.readLine()) != null) {

            /*
            Read up to 7 lines of Notations
            and saves it on a HashMap.
            */
            if(((line.substring(5,6)).equals("is")) && numLinhas<7){    
                String AlienLang = line.substring(0,4);
                String RomanNumeral = line.substring(8);
                dicionario.put(AlienLang,RomanNumeral);
                System.out.println(AlienLang+" "+RomanNumeral);
            }
            
            
            /*
            Read the info given: unidades, creditos and the type of the metal.
            And it saves the metal and its value in a HashMap.
            */
            else if(line.charAt(line.length()-1) != '?' && (line.substring(0,6).equals("quanto ")==false) || line.substring(0,6).equals("quantos")==false){
                int unidadesInt = decoder.romanToInt((dicionario.get(line.substring(0,3)) + dicionario.get(line.substring(5,8))));
                String metal = dicionario.get(line.substring(10,13));
                int creditos = decoder.romanToInt(dicionario.get(line.substring(20,21))); 
                int metalValor = creditos/unidadesInt;

                colecaoMetais.put(metal,metalValor);
                System.out.println(metal +" "+ metalValor);
            }

            /*
            Recongnizes that this a question "quanto vale"
            and calculates it with the numbers given.
            if the alien numbers given are not known it will print an error messege.
            */
            else if(numLinhas>7 && (line.substring(0,6).equals("quanto "))){
                    alien_Sound_1 = line.substring(12,15);
                    alien_Sound_2 = line.substring(17,20);
                    alien_Sound_3 = line.substring(22,25);
                    alien_Sound_4 = line.substring(27,30);

                if(((dicionario.containsKey(alien_Sound_1) == false   || 
                    (dicionario.containsKey(alien_Sound_2)) == false) || 
                    (dicionario.containsKey(alien_Sound_3)) == false) ||
                    (dicionario.containsKey(alien_Sound_4)) == false){
                        System.out.println("I have no idea what you are talking about");

                }

                int finalCreditos = decoder.romanToInt(dicionario.get(alien_Sound_1)
                                                    + (dicionario.get(alien_Sound_2))
                                                    + (dicionario.get(alien_Sound_3)
                                                    + (dicionario.get(alien_Sound_4))));

                System.out.println(alien_Sound_1 +" "+ alien_Sound_2 +" "+ alien_Sound_3 +" "+ alien_Sound_4 +" is "+ finalCreditos);
            }
            /*
            Recongnizes that this a question "quantos créditos são"
            and calculates it with the numbers and kind of metal given.
            if the alien numbers given or the metal are not known it will print an error messege.
            */
            if(line.charAt(line.length()-1) == '?' && (line.substring(0,6).equals("quantos"))){
                alien_Sound_1 = line.substring(21,24);
                alien_Sound_2 = line.substring(26,29);
                alien_Sound_3 = line.substring(31,34);

                if(((dicionario.containsKey(alien_Sound_1) == false) || 
                    (dicionario.containsKey(alien_Sound_2) == false) || 
                    (colecaoMetais.containsKey(alien_Sound_3) == false))){
                        System.out.println("I'm sorry Dave, I'm afraid I can't do that");
                }

                int finalUnidades = decoder.romanToInt(dicionario.get(alien_Sound_1) + (dicionario.get(alien_Sound_2)));
                int valorMetal = colecaoMetais.get(alien_Sound_3);
                int finalMetalValue = finalUnidades * valorMetal;

                System.out.println(alien_Sound_1 +" "+ alien_Sound_2 +" "+ alien_Sound_3 +" is "+ finalMetalValue);
            }

            numLinhas++;
            
        }
        br.close();

        }
        catch(IOException e)  
            {  
            e.printStackTrace();  
            }  
        
    }

}
/*
Saídas (do Teste)
pish tegj glob glob is 42
glob prok Silver is 68 Credits
glob prok Gold is 57800 Credits
glob prok Iron is 782 Credits
I have no idea what you are talking about
*/