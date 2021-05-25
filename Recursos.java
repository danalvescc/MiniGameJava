public class Recursos {
    private static Recursos singleton = null;

    private Recursos(){

    }

    public static Recursos getInstance(){
        if(singleton == null){
            singleton = new Recursos();
        }
        
        return singleton;
        
    }
    
    public int pontuacaoAtual = 0;
    public int record = 0;
}
