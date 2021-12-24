package v1;

public class InputHandler {
  boolean
    up,
    left,
    down,
    right,
    sprint,
    dig,
    flag,
    reset;
  
  public InputHandler(){}

  public void keyPressed(int keyCode){
    switch (keyCode) {
      // Movement keys
      case 38:
      up = true;
      break;
      case 37:
      left = true;
      break;
      case 40:
      down = true;
      break;
      case 39:
      right = true;
      break;
      case 16:
      sprint = true;
      break;
      // Other input
      case 68:
      dig = true;
      break;
      case 70:
      flag = true;
      break;
      case 82:
      reset = true;
      break;
      default:
      break;
    }
  }

  public void keyReleased(int keyCode){
    switch (keyCode) {
      // Movement keys
      case 38:
      up = false;
      break;
      case 37:
      left = false;
      break;
      case 40:
      down = false;
      break;
      case 39:
      right = false;
      break;
      case 16:
      sprint = false;
      break;
      // Other input
      case 68:
      dig = false;
      break;
      case 70:
      flag = false;
      break;
      case 82:
      reset = false;
      break;
      default:
      if (Settings.debug) {System.out.println(keyCode);}
      break;
    }
  }
}
