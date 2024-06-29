import java.awt.Color;

public class GameOverScreen {

    public void draw() {
        GameLib.setColor(Color.BLACK);
        GameLib.fillRect(GameLib.WIDTH / 2, GameLib.HEIGHT / 2, GameLib.WIDTH, GameLib.HEIGHT);

        GameLib.setColor(Color.WHITE);
        drawCenteredString("GAME OVER!", GameLib.WIDTH / 2, GameLib.HEIGHT / 2 - 50);
        drawCenteredString("APERTE ESC PARA SAIR", GameLib.WIDTH / 2 - 30, GameLib.HEIGHT / 2);  // Mover para a esquerda
        drawCenteredString("OU CTRL PARA REINICIAR", GameLib.WIDTH / 2 - 30, GameLib.HEIGHT / 2 + 50);  // Mover para a esquerda
    }

    private void drawCenteredString(String text, double centerX, double centerY) {
        double x = centerX - (text.length() * 8) / 2;
        for (char c : text.toCharArray()) {
            drawChar(c, x, centerY);
            x += 16; // Ajusta o espaçamento entre caracteres conforme necessário
        }
    }

    private void drawChar(char c, double x, double y) {
        switch (c) {
            case 'G': GameLib.drawLine(x, y, x + 8, y); GameLib.drawLine(x, y, x, y + 10); GameLib.drawLine(x, y + 10, x + 8, y + 10); GameLib.drawLine(x + 8, y + 10, x + 8, y + 5); GameLib.drawLine(x + 4, y + 5, x + 8, y + 5); break;
            case 'A': GameLib.drawLine(x, y + 10, x + 4, y); GameLib.drawLine(x + 4, y, x + 8, y + 10); GameLib.drawLine(x + 2, y + 5, x + 6, y + 5); break;
            case 'M': GameLib.drawLine(x, y + 10, x, y); GameLib.drawLine(x, y, x + 4, y + 5); GameLib.drawLine(x + 4, y + 5, x + 8, y); GameLib.drawLine(x + 8, y, x + 8, y + 10); break;
            case 'E': GameLib.drawLine(x, y, x + 8, y); GameLib.drawLine(x, y, x, y + 10); GameLib.drawLine(x, y + 5, x + 6, y + 5); GameLib.drawLine(x, y + 10, x + 8, y + 10); break;
            case 'O': GameLib.drawLine(x, y, x + 8, y); GameLib.drawLine(x, y, x, y + 10); GameLib.drawLine(x, y + 10, x + 8, y + 10); GameLib.drawLine(x + 8, y, x + 8, y + 10); break;
            case 'V': GameLib.drawLine(x, y, x + 4, y + 10); GameLib.drawLine(x + 8, y, x + 4, y + 10); break;
            case 'R': GameLib.drawLine(x, y + 10, x, y); GameLib.drawLine(x, y, x + 8, y); GameLib.drawLine(x + 8, y, x + 8, y + 5); GameLib.drawLine(x + 8, y + 5, x, y + 5); GameLib.drawLine(x + 8, y + 10, x, y + 5); break;
            case 'P': GameLib.drawLine(x, y + 10, x, y); GameLib.drawLine(x, y, x + 8, y); GameLib.drawLine(x + 8, y, x + 8, y + 5); GameLib.drawLine(x + 8, y + 5, x, y + 5); break;
            case 'S': GameLib.drawLine(x, y, x + 8, y); GameLib.drawLine(x, y, x, y + 5); GameLib.drawLine(x, y + 5, x + 8, y + 5); GameLib.drawLine(x + 8, y + 5, x + 8, y + 10); GameLib.drawLine(x, y + 10, x + 8, y + 10); break;
            case 'C': GameLib.drawLine(x, y, x + 8, y); GameLib.drawLine(x, y, x, y + 10); GameLib.drawLine(x, y + 10, x + 8, y + 10); break;
            case 'T': GameLib.drawLine(x, y, x + 8, y); GameLib.drawLine(x + 4, y, x + 4, y + 10); break;
            case 'L': GameLib.drawLine(x, y, x, y + 10); GameLib.drawLine(x, y + 10, x + 8, y + 10); break;
            case 'I': GameLib.drawLine(x + 4, y, x + 4, y + 10); break;
            case 'N': GameLib.drawLine(x, y + 10, x, y); GameLib.drawLine(x, y, x + 8, y + 10); GameLib.drawLine(x + 8, y + 10, x + 8, y); break;
            case 'H': GameLib.drawLine(x, y + 10, x, y); GameLib.drawLine(x + 8, y + 10, x + 8, y); GameLib.drawLine(x, y + 5, x + 8, y + 5); break;
            case 'U': GameLib.drawLine(x, y, x, y + 10); GameLib.drawLine(x, y + 10, x + 8, y + 10); GameLib.drawLine(x + 8, y, x + 8, y + 10); break;
            case '!': GameLib.drawLine(x + 4, y, x + 4, y + 7); GameLib.drawLine(x + 4, y + 8, x + 4, y + 9); break;
        }
    }
}
