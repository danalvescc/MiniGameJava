import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Game extends JPanel{
	private Bola bola;
	private Inimigo inimigo;
	private boolean k_cima = false;
	private boolean k_baixo = false;
	private BufferedImage imgAtual;
	private Recursos recurso = Recursos.getInstance();
	
	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP: k_cima=false; break;
				case KeyEvent.VK_DOWN: k_baixo=false; break;
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP: k_cima=true; break;
				case KeyEvent.VK_DOWN: k_baixo=true; break;
				}
			}
		});
		bola = new Bola();
		inimigo = new Inimigo();
		setFocusable(true);
		setLayout(null);
		
		new Thread(new Runnable() { // instancia da Thread + classe interna an�nima
			@Override
			public void run() {
				gameloop(); // inicia o gameloop
			}
		}).start(); // dispara a Thread
	}
	// GAMELOOP -------------------------------
	public void gameloop() {
		while(true) { // repeti��o intermitente do gameloop
			handlerEvents();
			update();
			render();
			try {
				Thread.sleep(17);
			}catch (Exception e) {}
		}
	}
	public void handlerEvents() {
		bola.velY = 0;
		imgAtual = bola.parada;
		if(k_cima==true) {
			bola.velY = -1 * bola.velocidade;
			imgAtual = bola.cima;
		} else if(k_baixo==true) {
			bola.velY = bola.velocidade;
			imgAtual = bola.baixo;
			
		}else{
			imgAtual = bola.parada;
		}
	}
	public void update() {
		bola.posY += bola.velY;

		inimigo.posX += inimigo.velX;
		testeColisoes();
	}
	public void render() {
		repaint();
	}
	
	// OUTROS METODOS -------------------------

	public void reiniciarPosicao(){
		bola = new Bola();
		inimigo = new Inimigo();
		
	}

	public void aumentarDificuldade(int velInimigo, int velBola, int InimigoPosX){
		if(velInimigo > 0){
			inimigo.velX = velInimigo + 1;
		}else{
			inimigo.velX = velInimigo - 1;
		}

		if(velInimigo > 0){
			bola.velocidade = (velInimigo/3) * 2;
		}else{
			bola.velocidade = (velInimigo/3) * -2;
		}

		inimigo.posX = InimigoPosX;

	}

	public void reiniciarGame(){
		reiniciarPosicao();
		recurso.pontuacaoAnterior = recurso.pontuacaoAtual;
		recurso.pontuacaoAtual = 0;
	}

	public void marcouPonto(){
		int velInimigo = inimigo.velX;
		int velBola = bola.velY;
		int InimigoPosX = inimigo.posX;
		reiniciarPosicao();
		recurso.pontuacaoAtual++;
		aumentarDificuldade(velInimigo, velBola, InimigoPosX);
	}

	public void testeColisoes() {
		// colis�o entre a bola e os lados da tela
		if(bola.posY <= 0) {
			bola.posY = bola.posY - bola.velY; // desfaz o movimento
		}

		//Marcou um ponto
		if (bola.posY + (bola.raio*2) >= Principal.ALTURA_TELA){
			marcouPonto();
		}
		
		// colis�o entre a bola e o inimigo
		int bolaCentroX = bola.posX + bola.raio;
		int bolaCentroY = bola.posY + bola.raio;

		int inimigoCentroX = inimigo.posX + inimigo.raio;
		int inimigoCentroY = inimigo.posY + inimigo.raio;

		int catetoH = Math.abs(bolaCentroX - inimigoCentroX);
		int catetoV = Math.abs(bolaCentroY - inimigoCentroY);
		double hipotenusa = Math.sqrt( (catetoV*catetoV) + (catetoH*catetoH) );
		if(hipotenusa <= bola.raio+inimigo.raio) {
			// desfaz o �ltimo movimento, para impedir a sobreposi��o
			bola.posY -= bola.velY;

			inimigo.posX -= inimigo.velX;
			
			reiniciarGame();
		}

		if(inimigo.posX + (inimigo.raio*2) >= Principal.LARGURA_TELA || inimigo.posX <= 0){
			inimigo.posX = inimigo.posX - inimigo.velX; // desfaz o movimento
			inimigo.velX *= -1;
		}
	}
		
	// METODO SOBRESCRITO ---------------------
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.LIGHT_GRAY);
		g.setColor(Color.RED);
		g.drawImage(imgAtual, bola.posX, bola.posY, null);
		g.drawImage(inimigo.img, inimigo.posX, inimigo.posY, null);

		g.drawString("Pontuação:", 10, 20);
		g.drawString(Integer.toString(recurso.pontuacaoAtual) ,10, 40 );
	}
}