import java.awt.Dimension;
import javax.swing.JFrame;

public class Principal {
	public static final int LARGURA_TELA = 1024;
	public static final int ALTURA_TELA = 480;
	
	public Principal() {
		JFrame janela = new JFrame("Jogo2D"); // cria a janela
		Game game = new Game(); // cria a tela do jogo
		game.setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
		janela.getContentPane().add(game); // adiciona a tela do jogo na janela
		janela.setResizable(false); // impede redimensionamento
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // modo de encerramento
		janela.setLocation(100, 100); // posi��o da janela na tela
		janela.setVisible(true); // torna a janela vis�vel
		janela.pack();
	}
	public static void main(String[] args) {
		new Principal(); // dispara a aplica��o
	}
}