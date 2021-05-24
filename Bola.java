import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Bola {	
	public BufferedImage cima; 
	public BufferedImage baixo;  
	public BufferedImage parada; 
	public int posX;
	public int posY;
	public int raio;
	public int velY;
	public int velocidade;

	
	public Bola() {
		try {
			cima = ImageIO.read(getClass().getResource("imgs/cima.gif"));
			baixo = ImageIO.read(getClass().getResource("imgs/baixo.gif"));
			parada = ImageIO.read(getClass().getResource("imgs/parada.gif"));
		}catch (Exception e) {
			System.out.println("Erro ao carregar a imagem!");
		}
		posX = Principal.LARGURA_TELA/2 - 50;
		posY = 0;
		raio = 50;
		velY = 0;
		velocidade = 2;
	}
}
