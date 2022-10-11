package Main;

public class InputHandler {
  private boolean[] isDown = new boolean[100];

  public static final int
    UP = 38,
    LEFT = 37,
    DOWN = 40,
    RIGHT = 39,
    SPRINT = 16,
    DIG = 68,
    FLAG = 70,
    RESET = 82;

  public InputHandler(){}

  public void keyPressed(int keyCode){
    isDown[keyCode] = true;
  }

  public void keyReleased(int keyCode){
    isDown[keyCode] = false;
    if (Settings.debug) {System.out.println(keyCode);}
  }

  public boolean isDown(int i) {
    return isDown[i];
  }
}
