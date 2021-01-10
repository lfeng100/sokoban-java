import java.awt.*;
import hsa.Console;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import sun.audio.*;

public class Sokoban extends JPanel implements KeyListener, MouseListener
{
    // Boolean variables to know which screen is being displayed.
    public boolean startup = true;
    public boolean init = true;
    public boolean stageInit = false;
    public boolean menuInit = true;
    public boolean levelSelectInit = false;
    public boolean instructionsInit = false;
    public int instructionsPage = 1;
    // Initializing image variables.
    public Image dbImage;
    public Graphics dbg;
    public static BufferedImage background = null;
    public static BufferedImage background2 = null;
    public static BufferedImage background3 = null;
    public static BufferedImage background4 = null;
    public static BufferedImage instructions = null;
    public static BufferedImage instructions2 = null;
    public static BufferedImage instructions3 = null;
    public static BufferedImage brick = null;
    public static BufferedImage ground = null;
    public static BufferedImage character = null;
    public static BufferedImage characterL = null;
    public static BufferedImage characterR = null;
    public static BufferedImage characterD = null;
    public static BufferedImage box = null;
    public static BufferedImage box2 = null;
    public static BufferedImage star = null;
    public static BufferedImage emptyStar = null;
    public static BufferedImage logo = null;
    public static BufferedImage lock = null;
    public static BufferedImage levelBlock = null;
    public static BufferedImage button = null;
    public static BufferedImage button2 = null;
    public static BufferedImage button3 = null;
    public static BufferedImage grey = null;
    public static BufferedImage x = null;
    // Game variables
    public Point endPoint = new Point (0, 0);
    public int moveCount = -1;
    public int twoStar = 0;
    public int threeStar = 0;
    public static int currentLevel = 0;
    public int levelStars = 3;
    public boolean levelComplete = false;
    public boolean[] levelCompletion = {false, false, false, false, false, false, false, false, false, false, false, false};
    // true, true, true, true, true, true, true, true, true, true, true, true
    public int[] starsLevel = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] [] level = null;
    
    // Implements Key and Mouse Listener
    public Sokoban ()
    {
	addKeyListener (this);
	addMouseListener (this);
    }

    // Abstract methods for KeyListener
    public void keyPressed (KeyEvent evt)
    {
	int keyCode = evt.getKeyCode ();
	int d;

	if (stageInit)
	{
	    d = 50;
	    if (keyCode == KeyEvent.VK_LEFT)
	    {
		move (-d, 0, false, true, false, false);
	    }
	    else if (keyCode == KeyEvent.VK_RIGHT)
	    {
		move (d, 0, true, false, false, false);
	    }
	    else if (keyCode == KeyEvent.VK_UP)
	    {
		move (0, -d, false, false, true, false);
	    }
	    else if (keyCode == KeyEvent.VK_DOWN)
	    {
		move (0, d, false, false, false, true);
	    }
	}
    }


    public void keyReleased (KeyEvent evt)
    {
    }


    public void keyTyped (KeyEvent evt)
    {
    }


    public boolean isFocusTraversable ()
    {
	return true;
    }


    public void mouseClicked (MouseEvent e)
    {
	if (startup)
	{
	    try
	    {
		Thread.sleep (1000);
	    }
	    catch (Exception f)
	    {
	    }
	    menu (0, 0);
	    startup = false;
	}
	else if (menuInit)
	{
	    menu (e.getX (), e.getY ());
	}
	else if (instructionsInit)
	{
	    instructions (e.getX (), e.getY ());
	}
	else if (levelSelectInit)
	{
	    levelSelect (e.getX (), e.getY ());
	}
	else if (levelComplete)
	{
	    levelSelect (0, 0);
	    variableInit ();
	}
	else if (stageInit)
	{
	    stageInit (e.getX (), e.getY ());
	}
    }


    public void mousePressed (MouseEvent e)
    {
    }


    public void mouseReleased (MouseEvent e)
    {
    }


    public void mouseEntered (MouseEvent e)
    {
    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void menu (int mx, int my)
    {
	Graphics g = getGraphics ();

	paintMenu (g);

	if (mx > getWidth () / 2 - 100 && mx < getWidth () / 2 + 100 && my > 200 && my < 250)
	{
	    menuInit = false;
	    levelSelectInit = true;
	    levelSelect (0, 0);
	}
	if (mx > getWidth () / 2 - 100 && mx < getWidth () / 2 + 100 && my > 275 && my < 325)
	{
	    menuInit = false;
	    instructionsInit = true;
	    instructions (0, 0);
	}
	if (mx > getWidth () / 2 - 100 && mx < getWidth () / 2 + 100 && my > 350 && my < 400)
	{
	    System.exit (0);
	}
    }


    public void paintMenu (Graphics g)
    {
	dbImage = createImage (getWidth (), getHeight ());
	dbg = dbImage.getGraphics ();
	paintMenuComponent (dbg);
	g.drawImage (dbImage, 0, 0, this);
    }


    public void paintMenuComponent (Graphics g)
    {
	super.paint (g);
	g.drawImage (background, 0, 0, 484, 511, this);

	g.drawImage (button, getWidth () / 2 - 100, 200, 200, 50, this);
	g.drawImage (button, getWidth () / 2 - 100, 275, 200, 50, this);
	g.drawImage (button, getWidth () / 2 - 100, 350, 200, 50, this);

	g.drawImage (logo, 0, 0, 479, 143, this);

	g.setFont (new Font ("Garamond", Font.PLAIN, 25));
	g.drawString ("By: Leo F.", 365, 150);

	g.setFont (new Font ("Garamond", Font.PLAIN, 50));
	g.setColor (Color.yellow);
	g.drawString ("Start", 190, 240);
	g.drawString ("Quit", 190, 385);

	g.setFont (new Font ("Garamond", Font.PLAIN, 35));
	g.drawString ("Instructions", 158, 310);
	g.dispose ();
    }


    public void instructions (int mx, int my)
    {
	Graphics g = getGraphics ();

	paintInstructions (g);

	if (mx > 25 && mx < 125 && my > 455 && my < 495)
	{
	    instructionsPage = 1;
	    instructionsInit = false;
	    menuInit = true;
	    menu (0, 0);
	}
	else if (instructionsPage == 1)
	{
	    if (mx > 195 && mx < 295 && my > 385 && my < 425)
	    {
		instructionsPage += 1;
		instructions (0, 0);
	    }
	}
	else if (instructionsPage == 2)
	{
	    if (mx > 270 && mx < 370 && my > 405 && my < 445)
	    {
		instructionsPage += 1;
		instructions (0, 0);
	    }
	    else if (mx > 130 && mx < 230 && my > 405 && my < 445)
	    {
		instructionsPage -= 1;
		instructions (0, 0);
	    }
	}
	else if (instructionsPage == 3)
	{
	    if (mx > 195 && mx < 295 && my > 405 && my < 445)
	    {
		instructionsPage -= 1;
		instructions (0, 0);
	    }
	}
    }


    public void paintInstructions (Graphics g)
    {
	dbImage = createImage (getWidth (), getHeight ());
	dbg = dbImage.getGraphics ();
	paintInstructionsComponent (dbg);
	g.drawImage (dbImage, 0, 0, this);
    }


    public void paintInstructionsComponent (Graphics g)
    {
	super.paint (g);
	g.drawImage (background4, 0, 0, 484, 511, this);

	g.setFont (new Font ("Garamond", Font.PLAIN, 35));
	g.drawImage (button2, 25, 455, 100, 40, this);
	g.setColor (Color.black);
	g.drawString ("Back", 39, 485);

	switch (instructionsPage)
	{
	    case 1:
		g.drawImage (instructions, 0, 0, 484, 511, this);
		g.drawImage (button3, 195, 385, 100, 40, this);
		g.drawString ("Next", 209, 415);
		break;
	    case 2:
		g.drawImage (instructions2, 0, 0, 484, 511, this);
		g.drawImage (button3, 270, 405, 100, 40, this);
		g.drawString ("Next", 284, 435);
		g.drawImage (button2, 130, 405, 100, 40, this);
		g.drawString ("Prev.", 144, 435);
		break;
	    case 3:
		g.drawImage (instructions3, 0, 0, 484, 511, this);
		g.drawImage (button2, 200, 405, 100, 40, this);
		g.drawString ("Prev.", 214, 435);
		break;
	}

	g.dispose ();
    }

    // Level Select Screen (Updates on mouse click)
    public void levelSelect (int mx, int my)
    {
	Graphics g = getGraphics ();

	paintLevelSelect (g);
	g.setFont (new Font ("Garamond", Font.PLAIN, 28));
	// Back to main menu.
	if (mx > 50 && mx < 174 && my > 417 && my < 467)
	{
	    levelSelectInit = false;
	    menuInit = true;
	    menu (0, 0);
	}
	// Choose one of the levels. If previous level not completed, level will not load.
	else if (mx > 50 && mx < 100 && my > 100 && my < 150)
	{
	    loadLevel (1, 200, 200, 10, 13);
	}
	else if (mx > 150 && mx < 200 && my > 100 && my < 150)
	{
	    if (levelCompletion [0] == true)
	    {
		loadLevel (2, 50, 50, 15, 19);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}

	    }
	}
	else if (mx > 250 && mx < 300 && my > 100 && my < 150)
	{
	    if (levelCompletion [1] == true)
	    {
		loadLevel (3, 50, 100, 15, 20);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
	else if (mx > 350 && mx < 400 && my > 100 && my < 150)
	{
	    if (levelCompletion [2] == true)
	    {
		loadLevel (4, 50, 50, 37, 43);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
	else if (mx > 50 && mx < 100 && my > 200 && my < 250)
	{
	    if (levelCompletion [3] == true)
	    {
		loadLevel (5, 50, 50, 93, 100);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
	else if (mx > 150 && mx < 200 && my > 200 && my < 250)
	{
	    if (levelCompletion [4] == true)
	    {
		loadLevel (6, 200, 200, 39, 45);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
	else if (mx > 250 && mx < 300 && my > 200 && my < 250)
	{
	    if (levelCompletion [5] == true)
	    {
		loadLevel (7, 50, 50, 45, 52);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
	else if (mx > 350 && mx < 400 && my > 200 && my < 250)
	{
	    if (levelCompletion [6] == true)
	    {
		loadLevel (8, 150, 250, 42, 50);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
	else if (mx > 50 && mx < 100 && my > 300 && my < 350)
	{
	    if (levelCompletion [7] == true)
	    {
		loadLevel (9, 300, 250, 62, 75);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
	else if (mx > 150 && mx < 200 && my > 300 && my < 350)
	{
	    if (levelCompletion [8] == true)
	    {
		loadLevel (10, 150, 250, 47, 55);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
	else if (mx > 250 && mx < 300 && my > 300 && my < 350)
	{
	    if (levelCompletion [9] == true)
	    {
		loadLevel (11, 250, 200, 73, 80);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (700);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
	else if (mx > 350 && mx < 400 && my > 300 && my < 350)
	{
	    if (levelCompletion [10] == true)
	    {
		loadLevel (12, 50, 100, 35, 40);
	    }
	    else
	    {
		try
		{
		    g.setColor (Color.yellow);
		    g.drawImage (grey, 0, 198, 600, 50, this);
		    g.drawString ("Please Complete Previous Level/s First!", 20, 230);
		    Thread.sleep (500);
		    levelSelect (0, 0);
		}
		catch (Exception e)
		{
		}
	    }
	}
    }

    // Double buffer for levelSelect
    public void paintLevelSelect (Graphics g)
    {
	dbImage = createImage (getWidth (), getHeight ());
	dbg = dbImage.getGraphics ();
	paintLevelSelectComponent (dbg);
	g.drawImage (dbImage, 0, 0, this);
    }

    // All the iamges and graphics for levelSelect screen drawn here.
    public void paintLevelSelectComponent (Graphics g)
    {
	super.paint (g);
	g.drawImage (background2, 0, 0, 484, 511, this);

	g.setFont (new Font ("Garamond", Font.PLAIN, 50));
	g.setColor (Color.yellow);
	g.drawImage (grey, 0, 30, 600, 60, this);
	g.drawString ("Level Select", 50, 73);

	g.drawImage (button2, 25, 417, 125, 50, this);
	g.setColor (Color.black);
	g.drawString ("Back", 39, 460);
	g.setFont (new Font ("Garamond", Font.BOLD, 25));
	for (int i = 0 ; (i / 2) < levelCompletion.length ; i += 2)
	{
	    int k = i / 2;
	    k %= 4;
	    int j = (i / 2) / 4 + 1;
	    int l = 0;
	    g.drawImage (levelBlock, k * 100 + 50, 100 * j, 50, 50, this);
	    g.setColor (Color.black);
	    if (i >= 18)
	    {
		l = 5;
	    }
	    g.drawString (Integer.toString (i / 2 + 1), k * 100 + 68 - l, j * 131 - (30 * (j - 1)));
	    try
	    {
		if (!levelCompletion [i / 2 - 1])
		{
		    g.drawImage (lock, k * 100 + 78, j * 100, 25, 25, this);
		}
	    }
	    catch (Exception e)
	    {
	    }
	}
	for (int j = 0 ; j < starsLevel.length ; j++)
	{
	    int k = j;
	    k %= 4;
	    int i = j / 4 + 1;
	    g.drawImage (emptyStar, 100 * k + 50, 150 * i - (50 * (i - 1)), 15, 15, this);
	    g.drawImage (emptyStar, 100 * k + 70, 150 * i - (50 * (i - 1)), 15, 15, this);
	    g.drawImage (emptyStar, 100 * k + 90, 150 * i - (50 * (i - 1)), 15, 15, this);
	    for (int h = 0 ; h < starsLevel [j] ; h++)
	    {
		g.drawImage (star, 100 * k + 50 + 20 * h, 150 * i - (50 * (i - 1)), 15, 15, this);
	    }
	}
	g.dispose ();
    }

    // Storage place for all the stage maps. Selects stage depending on currentLevel variable which is chosen on Level Select screen.
    public static int[] [] levelMap ()
    {
	int[] [] tempLevel = null;
	switch (currentLevel)
	{
	    case 1:
		int[] [] one =
		    {{5, 5, 1, 1, 1},
			{5, 5, 1, 2, 1},
			{5, 5, 1, 0, 1, 1, 1, 1},
			{1, 1, 1, 3, 0, 3, 2, 1},
			{1, 2, 0, 3, 0, 1, 1, 1},
			{1, 1, 1, 1, 3, 1},
			{5, 5, 5, 1, 2, 1},
			{5, 5, 5, 1, 1, 1}};
		tempLevel = one;
		break;
	    case 2:
		int[] [] two =
		    {{1, 1, 1, 1},
			{1, 0, 2, 1},
			{1, 3, 0, 1},
			{1, 0, 0, 1},
			{1, 0, 3, 1},
			{1, 2, 0, 1},
			{1, 0, 0, 1},
			{1, 1, 1, 1}};
		tempLevel = two;
		break;
	    case 3:
		int[] [] three =
		    {{5, 1, 1, 1, 1, 1},
			{1, 1, 0, 0, 2, 1},
			{1, 0, 3, 0, 0, 1},
			{1, 0, 0, 4, 0, 1},
			{1, 1, 1, 1, 1, 1}};
		tempLevel = three;
		break;
	    case 4:
		int[] [] four =
		    {{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 1, 0, 0, 0, 1},
			{1, 0, 0, 1, 2, 0, 0, 1},
			{1, 0, 0, 1, 0, 3, 0, 1},
			{1, 1, 0, 3, 0, 1, 1, 1},
			{5, 1, 0, 0, 0, 1},
			{5, 1, 2, 0, 0, 1},
			{5, 1, 1, 1, 1, 1}};
		tempLevel = four;
		break;
	    case 5:
		int[] [] five =
		    {{1, 1, 1, 1, 1},
			{1, 0, 0, 0, 1},
			{1, 0, 3, 3, 1, 0, 1, 1, 1},
			{1, 0, 3, 0, 1, 0, 1, 2, 1},
			{1, 1, 1, 0, 1, 1, 1, 2, 1},
			{5, 1, 1, 0, 0, 0, 0, 2, 1},
			{5, 1, 0, 0, 0, 1, 0, 0, 1},
			{5, 1, 0, 0, 0, 1, 1, 1, 1},
			{5, 1, 1, 1, 1, 1}};
		tempLevel = five;
		break;
	    case 6:
		int[] [] six =
		    {{1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 1},
			{1, 0, 2, 3, 0, 1},
			{1, 1, 2, 3, 1, 1, 1},
			{5, 1, 2, 3, 0, 0, 1},
			{5, 1, 0, 0, 0, 0, 1},
			{5, 1, 0, 0, 1, 1, 1},
			{5, 1, 1, 1, 1}};
		tempLevel = six;
		break;
	    case 7:
		int[] [] seven =
		    {{1, 1, 1, 1},
			{1, 0, 0, 1, 1, 1, 1, 1},
			{1, 0, 0, 3, 0, 3, 0, 1},
			{1, 0, 0, 2, 2, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1}};
		tempLevel = seven;
		break;
	    case 8:
		int[] [] eight =
		    {{5, 1, 1, 1, 1},
			{1, 1, 0, 0, 1},
			{1, 0, 3, 0, 1},
			{1, 2, 3, 2, 1},
			{1, 1, 3, 2, 1},
			{1, 0, 0, 0, 1},
			{1, 0, 0, 0, 1},
			{1, 1, 1, 1, 1}};
		tempLevel = eight;
		break;
	    case 9:
		int[] [] nine =
		    {{1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 0, 0, 0, 2, 0, 0, 0, 1},
			{1, 1, 0, 1, 4, 1, 1, 1, 1},
			{1, 0, 0, 0, 4, 0, 3, 0, 1},
			{1, 0, 0, 0, 4, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1}};
		tempLevel = nine;
		break;
	    case 10:
		int[] [] ten =
		    {{1, 1, 1, 1, 1, 1, 1},
			{1, 2, 0, 2, 0, 0, 1},
			{1, 2, 0, 4, 0, 0, 1},
			{1, 1, 0, 1, 0, 1, 1},
			{1, 0, 3, 3, 3, 0, 1},
			{1, 0, 0, 0, 6, 0, 1},
			{1, 1, 1, 1, 1, 1, 1}};
		tempLevel = ten;
		break;
	    case 11:
		int[] [] eleven =
		    {{5, 5, 1, 1, 1, 1, 0, 0, 0},
			{1, 1, 1, 0, 0, 1, 1, 1, 1},
			{1, 0, 0, 3, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 1, 1, 1, 0, 1},
			{1, 0, 1, 0, 4, 0, 1, 0, 1},
			{1, 0, 0, 0, 2, 1, 1, 0, 1},
			{1, 1, 1, 1, 0, 0, 0, 0, 1},
			{5, 5, 5, 1, 1, 1, 1, 1, 1}};
		tempLevel = eleven;
		break;
	    case 12:
		int[] [] twelve =
		    {{5, 5, 1, 1, 1, 1, 1},
			{1, 1, 1, 0, 0, 0, 1},
			{1, 2, 0, 3, 0, 0, 1},
			{1, 1, 1, 0, 3, 2, 1},
			{1, 2, 1, 1, 3, 0, 1},
			{1, 0, 1, 0, 2, 0, 1, 1},
			{1, 3, 0, 4, 3, 3, 2, 1},
			{1, 0, 0, 0, 2, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1}};
		tempLevel = twelve;
		break;
	}
	return tempLevel;
    }

    // From the levelSelect() method, the level's details are sent here and updated whenever a new level is loaded.
    public void loadLevel (int level, int x, int y, int three, int two)
    {
	levelSelectInit = false;
	stageInit = true;
	threeStar = three;
	twoStar = two;
	endPoint.x = x;
	endPoint.y = y;
	currentLevel = level;
	move (0, 0, false, false, false, false);
    }

    // When stag
    public void stageInit (int mx, int my)
    {
	if (mx > 320 && mx < 445 && my > 457 && my < 507)
	{
	    stageInit = false;
	    levelSelectInit = true;
	    variableInit ();
	    init = true;
	    levelSelect (0, 0);
	}
	if (mx > 25 && mx < 155 && my > 457 && my < 507)
	{
	    variableInit ();
	    levelSelectInit = false;
	    stageInit = true;
	    if (currentLevel == 1)
	    {
		endPoint.x = 200;
		endPoint.y = 200;
	    }
	    else if (currentLevel == 2)
	    {
		endPoint.x = 50;
		endPoint.y = 50;
	    }
	    else if (currentLevel == 3)
	    {
		endPoint.x = 50;
		endPoint.y = 100;
	    }
	    else if (currentLevel == 4)
	    {
		endPoint.x = 50;
		endPoint.y = 50;
	    }
	    else if (currentLevel == 5)
	    {
		endPoint.x = 50;
		endPoint.y = 50;
	    }
	    else if (currentLevel == 6)
	    {
		endPoint.x = 200;
		endPoint.y = 200;
	    }
	    else if (currentLevel == 7)
	    {
		endPoint.x = 50;
		endPoint.y = 50;
	    }
	    else if (currentLevel == 8)
	    {
		endPoint.x = 150;
		endPoint.y = 250;
	    }
	    else if (currentLevel == 9)
	    {
		endPoint.x = 300;
		endPoint.y = 250;
	    }
	    else if (currentLevel == 10)
	    {
		endPoint.x = 150;
		endPoint.y = 250;
	    }
	    else if (currentLevel == 11)
	    {
		endPoint.x = 250;
		endPoint.y = 200;
	    }
	    else if (currentLevel == 12)
	    {
		endPoint.x = 50;
		endPoint.y = 100;
	    }
	    move (0, 0, false, false, false, false);
	}
    }


    // Check if character is against a wall.
    public static boolean wallCheck (char direction, int ex, int ey, int[] [] array)
    {
	switch (direction)
	{
	    case 'r':
		if (array [ey / 50] [ex / 50 + 1] != 3 && array [ey / 50] [ex / 50 + 1] != 1 && array [ey / 50] [ex / 50 + 1] != 4)
		{
		    return true;
		}
		break;
	    case 'l':
		if (array [ey / 50] [ex / 50 - 1] != 3 && array [ey / 50] [ex / 50 - 1] != 1 && array [ey / 50] [ex / 50 - 1] != 4)
		{
		    return true;
		}
		break;
	    case 'u':
		if (array [ey / 50 - 1] [ex / 50] != 3 && array [ey / 50 - 1] [ex / 50] != 1 && array [ey / 50 - 1] [ex / 50] != 4)
		{
		    return true;
		}
		break;
	    case 'd':
		if (array [ey / 50 + 1] [ex / 50] != 3 && array [ey / 50 + 1] [ex / 50] != 1 && array [ey / 50 + 1] [ex / 50] != 4)
		{
		    return true;
		}
		break;
	}
	return false;
    }


    // Check if pushed block is against a wall or another block.
    public static boolean blockCheck (char direction, int ex, int ey, int[] [] array)
    {
	if ((array [ey / 50] [ex / 50] == 3 || array [ey / 50] [ex / 50] == 4))
	{
	    switch (direction)
	    {
		case 'r':
		    if (array [ey / 50] [ex / 50 + 1] == 3 || array [ey / 50] [ex / 50 + 1] == 1 || array [ey / 50] [ex / 50 + 1] == 4)
		    {
			return true;
		    }
		    break;
		case 'l':
		    if (array [ey / 50] [ex / 50 - 1] == 3 || array [ey / 50] [ex / 50 - 1] == 1 || array [ey / 50] [ex / 50 - 1] == 4)
		    {
			return true;
		    }
		    break;
		case 'u':
		    if (array [ey / 50 - 1] [ex / 50] == 3 || array [ey / 50 - 1] [ex / 50] == 1 || array [ey / 50 - 1] [ex / 50] == 4)
		    {
			return true;
		    }
		    break;
		case 'd':
		    if (array [ey / 50 + 1] [ex / 50] == 3 || array [ey / 50 + 1] [ex / 50] == 1 || array [ey / 50 + 1] [ex / 50] == 4)
		    {
			return true;
		    }
		    break;
	    }
	}
	return false;
    }


    // When a key is pressd, this move method is called and the screen updates.
    public void move (int dx, int dy, boolean r, boolean l, boolean u, boolean d)
    {
	// Change sprite location
	endPoint.x += dx;
	endPoint.y += dy;
	// If map is being loaded or reset, find the correct map from levelMap();
	if (init)
	{
	    level = levelMap ();
	    init = false;
	}
	// This direction String is passed to the paint() method so the sprite is facing the correct direction.
	String direction = "a";
	if (r)
	{
	    direction = "r";
	}
	else if (l)
	{
	    direction = "l";
	}
	if (u)
	{
	    direction = "u";
	}
	else if (d)
	{
	    direction = "d";
	}

	// Checks if character is moving into a wall, a block that is against a wall, or a block is against another block in blockCheck() method. If so, character's movement is negated.
	if ((r && level [endPoint.y / 50] [endPoint.x / 50] == 1)
		|| (r && blockCheck ('r', endPoint.x, endPoint.y, level))
		|| (r && blockCheck ('r', endPoint.x, endPoint.y, level)))
	{
	    endPoint.x -= dx;
	    moveCount--;
	}
	else if ((l && level [endPoint.y / 50] [endPoint.x / 50] == 1)
		|| (l && blockCheck ('l', endPoint.x, endPoint.y, level))
		|| (l && blockCheck ('l', endPoint.x, endPoint.y, level)))
	{
	    endPoint.x -= dx;
	    moveCount--;
	}
	else if ((u && level [endPoint.y / 50] [endPoint.x / 50] == 1)
		|| (u && blockCheck ('u', endPoint.x, endPoint.y, level))
		|| (u && blockCheck ('u', endPoint.x, endPoint.y, level)))
	{
	    endPoint.y -= dy;
	    moveCount--;
	}
	else if ((d && level [endPoint.y / 50] [endPoint.x / 50] == 1)
		|| (d && blockCheck ('d', endPoint.x, endPoint.y, level))
		|| (d && blockCheck ('d', endPoint.x, endPoint.y, level)))
	{
	    endPoint.y -= dy;
	    moveCount--;
	}

	// Checks if character is pushing a block and if pushed block is against a wall in wallCheck() method. If not, character and block are moved in levelUpdate() method.
	if (r && level [endPoint.y / 50] [endPoint.x / 50] == 3 && wallCheck ('r', endPoint.x, endPoint.y, level))
	{
	    level = levelUpdate (endPoint.x / 50, endPoint.y / 50, 3, "r", level);
	}
	else if (l && level [endPoint.y / 50] [endPoint.x / 50] == 3 && wallCheck ('l', endPoint.x, endPoint.y, level))
	{
	    level = levelUpdate (endPoint.x / 50, endPoint.y / 50, 3, "l", level);
	}
	else if (u && level [endPoint.y / 50] [endPoint.x / 50] == 3 && wallCheck ('u', endPoint.x, endPoint.y, level))
	{
	    level = levelUpdate (endPoint.x / 50, endPoint.y / 50, 3, "u", level);
	}
	else if (d && level [endPoint.y / 50] [endPoint.x / 50] == 3 && wallCheck ('d', endPoint.x, endPoint.y, level))
	{
	    level = levelUpdate (endPoint.x / 50, endPoint.y / 50, 3, "d", level);
	}
	else if (r && level [endPoint.y / 50] [endPoint.x / 50] == 4 && wallCheck ('r', endPoint.x, endPoint.y, level))
	{
	    level = levelUpdate (endPoint.x / 50, endPoint.y / 50, 3, "r1", level);
	}
	else if (l && level [endPoint.y / 50] [endPoint.x / 50] == 4 && wallCheck ('l', endPoint.x, endPoint.y, level))
	{
	    level = levelUpdate (endPoint.x / 50, endPoint.y / 50, 3, "l1", level);
	}
	else if (u && level [endPoint.y / 50] [endPoint.x / 50] == 4 && wallCheck ('u', endPoint.x, endPoint.y, level))
	{
	    level = levelUpdate (endPoint.x / 50, endPoint.y / 50, 3, "u1", level);
	}
	else if (d && level [endPoint.y / 50] [endPoint.x / 50] == 4 && wallCheck ('d', endPoint.x, endPoint.y, level))
	{
	    level = levelUpdate (endPoint.x / 50, endPoint.y / 50, 3, "d1", level);
	}

	// Depending on how many moves, player will get different amount of stars.
	moveCount++;
	if (moveCount <= threeStar)
	{
	    levelStars = 3;
	}
	else if (moveCount <= twoStar)
	{
	    levelStars = 2;
	}
	else
	{
	    levelStars = 1;
	}

	// Checks if all the blocks are in place, thus completing the level.
	if (checkCompletion (level))
	{
	    levelComplete = true;
	}

	Graphics g = getGraphics ();
	paint (g, level, levelStars, threeStar, direction);
    }


    // Checks if there any blocks that are not on the goals, if not, level will be complete.
    public boolean checkCompletion (int[] [] level)
    {
	for (int i = 0 ; i < level.length ; i++)
	{
	    for (int j = 0 ; j < level [i].length ; j++)
	    {
		if (level [i] [j] == 3)
		{
		    return false;
		}
	    }
	}
	return true;
    }


    // Double buffer paint method that creates an image of the whole screen before updating so that there is no flickering.
    public void paint (Graphics g, int[] [] level, int stars, int three, String direc)
    {
	dbImage = createImage (getWidth (), getHeight ());
	dbg = dbImage.getGraphics ();
	paintComponent (dbg, level, stars, three, direc);
	g.drawImage (dbImage, 0, 0, this);
    }


    // Draws the stage according to any player/block movement (Uses array to draw each element).
    public void paintComponent (Graphics g, int[] [] level, int stars, int three, String direc)
    {
	super.paint (g);
	g.drawImage (background3, 0, 0, 484, 511, this);

	// On my map array, 1 = wall, 2 = goal, 3 = block, 4 = block on goal, 5 = empty space.
	for (int i = 0 ; i < level.length ; i++)
	{
	    for (int j = 0 ; j < level [i].length ; j++)
	    {
		if (level [i] [j] != 5)
		{
		    g.drawImage (ground, j * 50, i * 50, 50, 50, this);
		}
		if (level [i] [j] == 1)
		{
		    g.drawImage (brick, j * 50, i * 50, 50, 50, this);
		}
		else if (level [i] [j] == 2)
		{
		    g.drawImage (x, j * 50, i * 50, 50, 50, this);
		}
		else if (level [i] [j] == 3)
		{
		    g.drawImage (box, j * 50, i * 50, 50, 50, this);
		}
		else if (level [i] [j] == 4)
		{
		    g.drawImage (box2, j * 50, i * 50, 50, 50, this);
		}
	    }
	}
	// Draws stars and move counter on bottom of screen.
	g.setFont (new Font ("Century", Font.PLAIN, 15));
	g.drawString ("Level ", 165, 475);
	g.drawString (Integer.toString (currentLevel), 212, 475);
	for (int i = 0 ; i < stars ; i++)
	{
	    g.drawImage (star, i * 25 + 230, 455, 25, 25, this);
	}
	g.setFont (new Font ("Century", Font.PLAIN, 25));
	g.drawString ("Moves: ", 165, 500);
	g.drawString (Integer.toString (moveCount), 255, 499);

	// Draws character sprite depending on direction (arugment is passed from move() method).
	if (direc.equals ("d"))
	{
	    g.drawImage (character, endPoint.x, endPoint.y, 50, 50, this);
	}
	else if (direc.equals ("u"))
	{
	    g.drawImage (characterD, endPoint.x, endPoint.y, 50, 50, this);
	}
	else if (direc.equals ("l"))
	{
	    g.drawImage (characterL, endPoint.x, endPoint.y, 50, 50, this);
	}
	else if (direc.equals ("r"))
	{
	    g.drawImage (characterR, endPoint.x, endPoint.y, 50, 50, this);
	}
	else
	{
	    g.drawImage (character, endPoint.x, endPoint.y, 50, 50, this);
	}

	// Back and Reset buttons
	g.setFont (new Font ("Garamond", Font.PLAIN, 50));
	g.drawImage (button2, 320, 457, 125, 50, this);
	g.setColor (Color.black);
	g.drawString ("Back", 330, 500);
	g.drawImage (button3, 25, 457, 130, 50, this);
	g.drawString ("Reset", 33, 500);
	// If level is completed, levelComplete overlay will come up, and player will be returned to Level Select screen.
	if (levelComplete)
	{
	    int q = 10;
	    if (currentLevel >= 10)
	    {
		q = 0;
	    }
	    g.drawImage (grey, 50, 50, 400, 400, this);
	    g.setFont (new Font ("Century", Font.PLAIN, 45));
	    g.setColor (Color.green);
	    g.drawString ("Level", 60 + q, 100);
	    g.drawString (Integer.toString (currentLevel), 185 + q, 100);
	    g.drawString ("Complete", 240 - q, 100);
	    // If the player did not complete the level optimally (three stars), it will tell them how many moves they need.
	    for (int i = 0 ; i < 3 ; i++)
	    {
		g.drawImage (emptyStar, i * 50 + 165, 200, 50, 50, this);
	    }
	    for (int i = 0 ; i < stars ; i++)
	    {
		g.drawImage (star, i * 50 + 165, 200, 50, 50, this);
	    }
	    if (stars != 3)
	    {
		g.setFont (new Font ("Garamond", Font.PLAIN, 25));
		g.setColor (Color.red);
		g.drawString ("For three stars, complete the level in: ", 60, 150);
		g.setFont (new Font ("Garamond", Font.PLAIN, 45));
		g.setColor (Color.yellow);
		g.drawString (Integer.toString (three), 145, 190);
		g.drawString ("Moves", 193, 190);
	    }
	    else
	    {
		g.setColor (Color.yellow);
		g.drawString ("PERFECT!", 125, 180);
	    }
	    g.drawImage (character, 190, 260, 100, 100, this);
	    g.setColor (Color.white);
	    g.setFont (new Font ("Garamond", Font.PLAIN, 33));
	    g.drawString ("Click anywhere to continue", 60, 400);
	    // levelCompletion array is updated depending on what level was completed. starsLevel array is updated depending on how many stars they received on the level.
	    levelCompletion [currentLevel - 1] = true;
	    if (starsLevel [currentLevel - 1] <= stars)
	    {
		starsLevel [currentLevel - 1] = stars;
	    }
	    // Current screen is back to Level Select.
	}
	g.dispose ();
    }


    // Initialization of images from directory.
    public static void imageInit () throws IOException
    {
	background = ImageIO.read (new File ("images/background.png"));
	background2 = ImageIO.read (new File ("images/background2.png"));
	background3 = ImageIO.read (new File ("images/background3.png"));
	background4 = ImageIO.read (new File ("images/background4.png"));
	instructions = ImageIO.read (new File ("images/instructions.png"));
	instructions2 = ImageIO.read (new File ("images/instructions2.png"));
	instructions3 = ImageIO.read (new File ("images/instructions3.png"));
	brick = ImageIO.read (new File ("images/brick.png"));
	ground = ImageIO.read (new File ("images/ground.png"));
	character = ImageIO.read (new File ("images/character.png"));
	characterL = ImageIO.read (new File ("images/characterL.png"));
	characterR = ImageIO.read (new File ("images/characterR.png"));
	characterD = ImageIO.read (new File ("images/characterD.png"));
	box = ImageIO.read (new File ("images/box.jpg"));
	box2 = ImageIO.read (new File ("images/box2.png"));
	star = ImageIO.read (new File ("images/star.png"));
	emptyStar = ImageIO.read (new File ("images/emptyStar.png"));
	lock = ImageIO.read (new File ("images/lock.png"));
	logo = ImageIO.read (new File ("images/logo.png"));
	levelBlock = ImageIO.read (new File ("images/level.png"));
	button = ImageIO.read (new File ("images/button.png"));
	button2 = ImageIO.read (new File ("images/button2.png"));
	button3 = ImageIO.read (new File ("images/button3.png"));
	grey = ImageIO.read (new File ("images/grey.png"));
	x = ImageIO.read (new File ("images/x.png"));
    }

    // Resets all variables when stage is loaded or reset.
    public void variableInit ()
    {
	init = true;
	stageInit = false;
	menuInit = false;
	levelSelectInit = true;
	moveCount = -1;
	levelStars = 3;
	levelComplete = false;
    }

    // Updates the map array depending on block character movement.
    public static int[] [] levelUpdate (int x, int y, int z, String direc, int[] [] levelUpdate)
    {
	if (!direc.equals ("a"))
	{
	    if (direc.equals ("r"))
	    {
		levelUpdate [y] [x] = 0;
		if (levelUpdate [y] [x + 1] == 2)
		{
		    levelUpdate [y] [x + 1] = z + 1;
		}
		else
		{
		    levelUpdate [y] [x + 1] = z;
		}
	    }
	    else if (direc.equals ("l"))
	    {
		levelUpdate [y] [x] = 0;
		if (levelUpdate [y] [x - 1] == 2)
		{
		    levelUpdate [y] [x - 1] = z + 1;
		}
		else
		{
		    levelUpdate [y] [x - 1] = z;
		}
	    }
	    else if (direc.equals ("u"))
	    {
		levelUpdate [y] [x] = 0;
		if (levelUpdate [y - 1] [x] == 2)
		{
		    levelUpdate [y - 1] [x] = z + 1;
		}
		else
		{
		    levelUpdate [y - 1] [x] = z;
		}
	    }
	    else if (direc.equals ("d"))
	    {
		levelUpdate [y] [x] = 0;
		if (levelUpdate [y + 1] [x] == 2)
		{
		    levelUpdate [y + 1] [x] = z + 1;
		}
		else
		{
		    levelUpdate [y + 1] [x] = z;
		}
	    }
	    else if (direc.equals ("r1"))
	    {
		levelUpdate [y] [x] = 2;
		if (levelUpdate [y] [x + 1] == 2)
		{
		    levelUpdate [y] [x + 1] = z + 1;
		}
		else
		{
		    levelUpdate [y] [x + 1] = z;
		}
	    }
	    else if (direc.equals ("l1"))
	    {
		levelUpdate [y] [x] = 2;
		if (levelUpdate [y] [x - 1] == 2)
		{
		    levelUpdate [y] [x + 1] = z + 1;
		}
		else
		{
		    levelUpdate [y] [x - 1] = z;
		}
	    }
	    else if (direc.equals ("u1"))
	    {
		levelUpdate [y] [x] = 2;
		if (levelUpdate [y - 1] [x] == 2)
		{
		    levelUpdate [y - 1] [x] = z + 1;
		}
		else
		{
		    levelUpdate [y - 1] [x] = z;
		}
	    }
	    else if (direc.equals ("d1"))
	    {
		levelUpdate [y] [x] = 2;
		if (levelUpdate [y + 1] [x] == 2)
		{
		    levelUpdate [y + 1] [x] = z + 1;
		}
		else
		{
		    levelUpdate [y + 1] [x] = z;
		}
	    }
	}
	return levelUpdate;
    }


    // Plays background music
    public static void music ()
    {
	AudioPlayer MGP = AudioPlayer.player;
	AudioStream BGM;
	AudioData MD;

	ContinuousAudioDataStream loop = null;

	try
	{
	    InputStream test = new FileInputStream ("sounds/background.wav");
	    BGM = new AudioStream (test);
	    AudioPlayer.player.start (BGM);
	}
	catch (Exception e)
	{
	}
	MGP.start (loop);
    }


    public static void main (String[] args) throws IOException
    {
	JFrame frame = new JFrame ();
	frame.setTitle ("Sokoban");
	frame.setSize (500, 550);
	frame.addWindowListener (new WindowAdapter ()
	{
	    public void windowClosing (WindowEvent e)
	    {
		System.exit (0);
	    }
	}
	);

	Container contentPane = frame.getContentPane ();
	contentPane.add (new Sokoban ());

	frame.show ();
	music ();
	imageInit ();
    }
}


