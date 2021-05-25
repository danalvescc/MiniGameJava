import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Background {
    public BufferedImage img;

    public Background(){
        try {
            img = ImageIO.read(getClass().getResource("imgs/background.gif"));
        }catch (Exception e) {
            System.out.println("Erro ao carregar a imagem!");
        }
    }
}
