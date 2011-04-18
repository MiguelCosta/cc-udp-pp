/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author goku
 */
public class Main {
    static Scanner s;
    static String nome;
    static ArrayList<String> tipos;
    static ArrayList<String> nomes;
    static ArrayList<String> terminacoes;
    static ArrayList<String> prim_maiuscula;
    static BufferedWriter fich;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            s = new Scanner(System.in);

            System.out.println("Nome da classe:");
            nome = s.next();

            String nome_ficheiro = nome + ".java";

            fich = new BufferedWriter(new FileWriter(nome_ficheiro));

            tipos = new ArrayList<String>();
            nomes = new ArrayList<String>();

            System.out.println("Tipo de Dado");

            String data = s.next();
            while ( !data.equals("end") ){
                tipos.add(data);
                System.out.println("Nome de Dado");
                data = s.next();
                nomes.add(data);
                System.out.println("Tipo de Dado");
                data = s.next();
            }

            fich.write("\n\npublic class " + nome + "{\n");

            getTerminacoes();
            prim_maiuscula();
            fich.write("\t/** Variaveis de instancia */\n");
            escreve_variaveis_instancias();
            fich.write("\n\n\t/** Construtores */\n");
            escreve_construtores();
            fich.write("\n\n\t/** gets */\n");
            escreve_gets();
            fich.write("\n\n\t/** sets */\n");
            escreve_sets();
            fich.write("\n\n\t/** Equals | Clone | toString */\n");
            escreve_outros();

            fich.write("}\n");
            fich.close();

        } catch (IOException ex) {
            System.out.println("[ERRO main] : " + ex.getMessage());
        }

    }

    static void getTerminacoes(){
        terminacoes = new ArrayList<String>();
        String term;

        for (String str : tipos){
            if (str.equals("int") || str.equals("double") || str.equals("float") || str.equals("short") || str.equals("long"))
                term = "0";
            else if (str.equals("String"))
                term = "\"\"";
            else if (str.equals("boolean"))
                term = "false";
            else if (str.equals("char"))
                term = null;
            else
                term = "new " + str + "()";

            terminacoes.add(term);
        }

    }

    public static void prim_maiuscula(){
        prim_maiuscula = new ArrayList<String>();

        String term;

        for (String str : nomes){
            term = str.charAt(0) + "";
            term = term.toUpperCase();
            term = term + str.substring(1);

            prim_maiuscula.add(term);
        }
    }

    public static void escreve_variaveis_instancias(){
        try {
            for (int i = 0 ; i < tipos.size() ; i++)
                fich.write("\tprivate " + tipos.get(i) + " " + nomes.get(i) + ";\n");
        } catch (IOException ex) {
            System.out.println("[ERRO var_inst] : " + ex.getMessage());
        }
    }

    static void escreve_construtores(){
        try {
            /* Construtor normal */
            fich.write("\tpublic " + nome + "(){\n");
            for (int i = 0 ; i < tipos.size() ; i++)
                fich.write("\t\t" + nomes.get(i) + " = " + terminacoes.get(i) +";\n");
            fich.write("\t}\n");

            /* Construtor que recebe parametros */
            fich.write("\tpublic " + nome + "(");
            for (int i = 0 ; i < tipos.size() ; i++){
                if (i!=0)
                    fich.write(", ");
                fich.write(tipos.get(i) + " " + nomes.get(i));
            }
            fich.write("){\n");
            for (int i = 0 ; i < tipos.size() ; i++)
                fich.write("\t\tthis." + nomes.get(i) + " = " + nomes.get(i) + ";\n");
            fich.write("\t}\n");

            /* Construtor que recebe objecto */
            fich.write("\tpublic " + nome + "(" + nome + " " + nome.toLowerCase() + "){\n");
            for (int i = 0 ; i < tipos.size() ; i++)
                fich.write("\t\t" + nomes.get(i) + " = " + nome.toLowerCase() + ".get" + prim_maiuscula.get(i) + "()" + ";\n");
            fich.write("\t}\n");

        } catch (IOException ex) {
            System.out.println("[ERRO constr] : " + ex.getMessage());
        }

    }

    static void escreve_gets(){
        try {
            for (int i = 0 ; i < tipos.size() ; i++)
                fich.write("\tpublic " + tipos.get(i) + " get" + prim_maiuscula.get(i) + "(){ return " + nomes.get(i) + "; }\n");

        } catch (IOException ex) {
            System.out.println("[ERRO constr] : " + ex.getMessage());
        }
    }

    static void escreve_sets(){
        try {
            for (int i = 0 ; i < tipos.size() ; i++){
                fich.write("\tpublic void set" + prim_maiuscula.get(i) + "(" + tipos.get(i) + " " + nomes.get(i) + "){ ");
                fich.write("this." + nomes.get(i) + " = " + nomes.get(i) + ";}\n");
            }

        } catch (IOException ex) {
            System.out.println("[ERRO constr] : " + ex.getMessage());
        }
    }

    static void escreve_outros(){
        try {
            /* equals */
            
            fich.write("\t@Override\n");
            fich.write("\tpublic boolean equals(Object o){\n");
            fich.write("\t\tif (this == o) { return true; }\n");
            fich.write("\t\tif (o == null || o.getClass() != this.getClass()) { return false; }\n");
            fich.write("\t\t" + nome + " " + nome.toLowerCase() + " = (" + nome + ") o;\n");
            fich.write("\t\tif (");
            for (int i = 0 ; i < nomes.size() ; i++){
                if (i!=0)
                    fich.write(" && ");
                fich.write("this." + nomes.get(i) + " == " + nome.toLowerCase() + ".get" + prim_maiuscula.get(i) + "()");
            }
            fich.write("){\n\t\t\treturn true;\n\t\t} else { \n \t\t\treturn false;\n\t\t}\n\t}\n\n");

            /* clone */
            fich.write("\t@Override\n");
            fich.write("\tpublic " + nome + " clone(){\n");
            fich.write("\t\treturn new " + nome +"(this);\n\t}\n\n");

            /* toString*/
            fich.write("\t@Override\n");
            fich.write("\tpublic String toString(){\n");
            fich.write("\t\tStringBuilder s = new StringBuilder(\"" + nome.toUpperCase() + ":\\n\");\n");
            for (int i = 0 ; i < nomes.size() ; i++)
                fich.write("\t\ts.append(\"" + prim_maiuscula.get(i) + ": \" + " + nomes.get(i) + " + \"\\n\");\n");

            fich.write("\t\treturn s.toString();\n\t}\n");
        } catch (IOException ex) {
            System.out.println("[ERRO constr] : " + ex.getMessage());
        }
    }

}
