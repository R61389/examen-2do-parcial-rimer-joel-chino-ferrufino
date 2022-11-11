import java.util.Scanner;

/**
 *
 * @author destructor
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static int[][] notas;
    private static String[][] estudiantes;
    private static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        notas = new int[180][3];
        estudiantes = new String[180][2];
        inicializar();
        while(true){
        do {
            System.out.println("Ingrese el numero de lo que desea hacer:\n"
                    + "1-. Ingresar Estudiante\n"
                    + "2-. Ingresar Nota Estudiante\n"
                    + "3-. Retirar Estudiante\n"
                    + "4-. Mostrar lista aprobados y reprobados\n"
                    + "5-. Mostrar media aprobados y reprobados");
            System.out.print("->");
            opcion = Integer.parseInt(entrada.nextLine());
            switch (opcion) {
                case 1:
                    ingresarEstudiante();
                    break;
                case 2:
                    IgresoNota();
                    break;
                case 3:
                    borrarEstudiante();
                    break;
                case 4:
                    System.out.println("La lista de aprobados es");
                    mostrarListaAprobados();

                    System.out.println("La lista de reprobados es");
                    mostrarListaReprobados();
                    break;
                case 5:
                    System.out.println("La media de los aprobados es:");
                    mediaAprobados();
                    System.out.println("La media de los reprobados es:");
                    mediaReprobados();
                    break;
                default:
                    System.out.println("Error opcion no encontrada vuelva a ingresar de nuevo la opcion");
                    break;
            }
        } while (opcion < 1 || opcion > 5);
        }
    }

    public static void IgresoNota() {
        String codigo;
        int estu;
        do {
            System.out.println("Ingrese el codigo del estudiante:");
            codigo = entrada.nextLine();
            estu = buscarEstudiante(codigo);
            if (estu <= -1) {
                System.out.println("error no se encontro al estudiante");
            } else {
                CambiarNota("ingresar", estu);
            }
        } while (estu <= -1);
    }

    public static void CambiarNota(String dato, int estu) {
        int opcion, nota;
        do {
            System.out.println("Ingrese el numero del parcial al que desea " + dato + " la nota:\n"
                    + "1-. Primer parcial\n"
                    + "2-. Segundo parcial\n"
                    + "3-. Examen final");
            System.out.print("->");
            opcion = Integer.parseInt(entrada.nextLine());
            if (opcion < 1 || opcion > 3) {
                System.out.println("Error no ingresaste una opcion valida");
            } else {
                System.out.println("por favor ingrese la nota de la opcion " + opcion + ":");
                nota = Integer.parseInt(entrada.nextLine());
                notas[estu][opcion-1] = nota;
            }
        } while (opcion < 1 || opcion > 3);
    }
    private static int cantidad=0;
    private static void ingresarEstudiante() {
        String NombreCompleto, codigo;
        int estu;
        if (verificarVacancia()) {
            do {
                System.out.println("Por favor ingrese el codigo del estudiante:");
                codigo = entrada.nextLine();
                estu = buscarEstudiante(codigo);
                if (estu <= -1) {
                    System.out.println("Por favor ingrese los nombres del estudiante:");
                    NombreCompleto = entrada.nextLine().toUpperCase();
                    System.out.println("Por favor ingrese los apellidos del estudiante:");
                    NombreCompleto +=" "+ entrada.nextLine().toUpperCase();
                    estudiantes[cantidad][0] = codigo;
                    estudiantes[cantidad][1] = NombreCompleto;
                    cantidad++;
                } else {
                    System.out.println("Ya hay un estudiante con ese codigo");
                }
            } while (estu > -1);
        } else {
            System.out.println("Error no hay mas bacancia");
        }
    }

    public static int buscarEstudiante(String codigo) {
        int bandera = -1;
        if(verificarSiHayEstudiantes()){
        for (int n = 0; n < estudiantes.length; n++) {
            if(estudiantes[n][0]!=null){
            if (estudiantes[n][0].equals(codigo)) {
                bandera = n;
                break;
            }
            }
        }
        }
        return bandera;
    }

    public static boolean verificarVacancia() {
        int bandera = 0;
        for (int n = 0; n < estudiantes.length; n++) {
            if (estudiantes[n][0]==null) {
                bandera++;
                break;
            }
        }
        return bandera > 0;
    }
     public static boolean verificarSiHayEstudiantes() {
        int bandera = 0;
        for (int n = 0; n < estudiantes.length; n++) {
            if (estudiantes[n][0]!=null) {
                bandera++;
                break;
            }
        }
        return bandera > 0;
    }

    public static void borrarEstudiante() {
        String codigo;
        int estu;

        do {
            System.out.println("Por favor ingrese el codigo del estudiante:");
            codigo = entrada.nextLine();
            estu = buscarEstudiante(codigo);
            if (estu <= -1) {
                System.out.println("Error el codigo del estudiante no se encontro");
            } else {
                cantidad--;
                estudiantes[estu] = null;
                notas[estu] = null;
                System.gc();
                estudiantes[estu] = new String[2];
                notas[estu] = new int[3];
                normalizarEstudiantes(estu);
            }
        } while (estu <= -1);
    }

    private static void mostrarListaAprobados() {
        double total;
        for (int n = 0; n < 180; n++) {
            total = (notas[n][0] + notas[n][1] + notas[n][2]) / 3.0;
            if (total >= 51) {
                System.out.println(estudiantes[n][0] + " " + estudiantes[n][1]);
            }
        }
    }

    private static void mostrarListaReprobados() {
        double total;
        for (int n = 0; n < 180; n++) {
            total = (notas[n][0] + notas[n][1] + notas[n][2]) / 3.0;
            if (total < 51&&total>0) {
                System.out.println(estudiantes[n][0] + " " + estudiantes[n][1]);
            }
        }
    }

    private static void mediaAprobados() {
       double total;
       double calculo=0;
       int cant=0;
        for (int n = 0; n < 180; n++) {
            total = (notas[n][0] + notas[n][1] + notas[n][2]) / 3.0;
            if (total >= 51) {
                calculo+=total;
                cant++;
            }
        }
        if(cant>0){
        System.out.println("La media es:"+(double)calculo/(double)cant);
        }
        else{
            System.out.println("no hay estudiantes aprobados para sacar una media");
        }
    }

    private static void mediaReprobados() {
  double total;
       double calculo=0;
       int cant=0;
        for (int n = 0; n < 180; n++) {
            total = (notas[n][0] + notas[n][1] + notas[n][2]) / 3.0;
            if (total < 51&&total>0) {
                calculo+=total;
                cant++;
            }
        }
        if(cant>0){
        System.out.println("La media es:"+(double)calculo/(double)cant);
        }
        else{
            System.out.println("no hay estudiantes reprobados para sacar una media");
        }
    
    }

    private static void inicializar() {
        for(int [] dato :notas){
            dato=new int[3];
        }
        for(String [] estu:estudiantes){
            estu=new String[2];
        }
    }

    private static void normalizarEstudiantes(int p) {
        for(int n=p; n<180-1; n++){
            notas[n]=notas[n+1];
            estudiantes[n]=estudiantes[n+1];
        }
    }
}