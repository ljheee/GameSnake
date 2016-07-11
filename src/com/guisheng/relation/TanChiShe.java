package com.guisheng.relation;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.*;
import java.awt.event.*;
import java.awt.peer.LightweightPeer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
public class TanChiShe extends JFrame implements ActionListener, KeyListener,Runnable{
	
	private static boolean paused = false;
	JMenuBar menuBar;
	JMenu youXi, ny, fs, gy;
	JMenuItem ks, tc, zz, fsi,jd, yb, kn,zj;	
	int length ;
	Toolkit toolkit;
	int i, x, y, z, objectX, objectY, object = 0, growth = 0, time;
	int difficult = 2;
	int m[] = new int[50];
	int n[] = new int[50];
	Thread she = null;
	int life = 0;
	int foods = 0;
	int fenshu = 0;
	public int index = 500;
	
	MyTask task = new MyTask();
	Timer timer = new  Timer();
	
	public void run() {
		
		
		time = index;
		for (i = 0; i <= length - 1; i++) {
			m[i] = 90- i * 10;
			n[i] = 60;
		}

		x = m[0];
		y = n[0];
		z = 4;
		while (she != null) {
			check();
			try {
				she.sleep(time);
			}

			catch (Exception ee) {
				System.out.println(z + "");
			}
				
			if(!paused){
				repaint();
			}
		}

	}

	public TanChiShe() {

		menuBar = new JMenuBar();
		Container con = getContentPane();
		toolkit=getToolkit();
		youXi = new JMenu("��Ϸ");
		ks = new JMenuItem("��ʼ��Ϸ");
		zj = new JMenuItem("��ͣ/����");
		tc = new JMenuItem("�˳�");
		ny = new JMenu("���׳̶�");
		jd = new JMenuItem("��");
		yb = new JMenuItem("һ��");
		kn = new JMenuItem("����");
		fs = new JMenu("��������");
		fsi = new JMenuItem("��߼�¼");
		gy = new JMenu("����");
		zz = new JMenuItem("��������");
		menuBar.add(youXi);
		menuBar.add(ny);
		menuBar.add(fs);
		menuBar.add(gy);
		youXi.add(ks);
		youXi.add(tc);
		youXi.add(zj);
		ny.add(jd);
		ny.add(yb);
		ny.add(kn);
		fs.add(fsi);
		gy.add(zz);
		
		ks.addActionListener(this);
		tc.addActionListener(this);
		fsi.addActionListener(this);
		zz.addActionListener(this);		
		addKeyListener(this);
				
		KeyStroke keyOpen = KeyStroke.getKeyStroke('O',
				InputEvent.CTRL_DOWN_MASK);
		ks.setAccelerator(keyOpen);
		KeyStroke keyExit = KeyStroke.getKeyStroke('X',
				InputEvent.CTRL_DOWN_MASK);
		
		tc.setAccelerator(keyExit);
		setJMenuBar(menuBar);
		
		validate(); // ˢ�����
		setTitle("̰����");
		setResizable(false);
		setBounds(400, 200, 400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
//		timer.schedule(task, 0,10);
	}


	public void actionPerformed(ActionEvent e)

	{
		if (e.getSource() == ks) {
			length = 2;
			life = 0;
			foods = 0;
			if (she == null) {
				she = new Thread(this);
				she.start();
			} else if (she != null) {
				she = null;
				she = new Thread(this);
				she.start();
			}
		}

		if (e.getSource() == tc) {
			System.exit(0);
		}
		if (e.getSource() == zz) {
			JOptionPane.showMessageDialog(this, "����" + "\n\n" + "\n");
		}
		if (e.getSource() == fsi) {
			JOptionPane.showMessageDialog(this, " ��߼�¼Ϊ" + fenshu + "");
		}
		if (e.getSource() == zj) {
			if(she!=null){
				index = 500000000;
				check();
			}
		}
	}

	public void check() {
		isDead();
		if (she != null) {
			if (growth == 0) {
				reform();
			} else {
				upgrowth();
			}
			if (x == objectX && y == objectY) {
				object = 0;
				growth = 1;
				toolkit.beep();
			}
			if (object == 0) {
				object = 1;
				objectX = (int) Math.floor(Math.random() * 39) * 10;
				objectY = (int) Math.floor(Math.random() * 29) * 10 + 50;
			}
			this.repaint();
		}
	}

	void isDead()

	{
		
		if (z == 4) {
			x = x + 10;  
		} else if (z == 3) {
			x = x - 10;
		} else if (z == 2) {
			y = y + 10;
		} else if (z == 1) {
			y = y - 10;
		}
		if (x < 0 || x > 390 || y < 50 || y > 390) {
			she = null;
			int choose=JOptionPane.showConfirmDialog(this, "GAMEOVER����������������" + "\n"+ "�Ƿ�����һ�Σ�" + "\n");
			if(choose==JOptionPane.YES_NO_OPTION){
				if(z!=2)
					z=(z+2)% 4;
				else
					z=4;		    
				she = new Thread(this);				
				she.start();
				length = 2;
				life=0;
				foods=0;
			}
			if(choose==JOptionPane.NO_OPTION){
				System.exit(0);
				}
		}
		for (i = 1; i < length; i++) {
			if (m[i] == x && n[i] == y) {
				she = null;
				JOptionPane.showMessageDialog(this, "GAMEOVER��������������" + "\n\n" + "\n");
				
			}
		}
	}

	public void upgrowth() {
		if (length < 50) {
			length++;
		}
		growth--;
		time = time - 10;
		reform();
		life += 100;
		if (fenshu < life) {
			fenshu = life;
		}
		foods++;
	}

	public void reform() {
		for (i = length - 1; i > 0; i--) {
			m[i] = m[i - 1];
			n[i] = n[i - 1];
		}
		if (z == 4) {
			m[0] = m[0] + 10;
		}
		if (z == 3) {
			m[0] = m[0] - 10;
		}
		if (z == 2) {
			n[0] = n[0] + 10;
		}
		if (z == 1) {
			n[0] = n[0] - 10;
		}

	}

	public void keyPressed(KeyEvent e) {
		if (she != null) {
			
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_UP:
			{
				if (z != 2) {
					z = 1;
					check();
				}
				break; 
			}
			case KeyEvent.VK_SPACE:
		    case KeyEvent.VK_B:
			  // she.changePauseState();
				
				break;
				
			
			
			
				
			case KeyEvent.VK_DOWN:
			{
				
				if (z != 1) {
					z = 2;
					check();
				}
				break;
			}
			case KeyEvent.VK_LEFT:
			{
				if (z != 4) {
					z = 3;
					check();
				}
				break;
			}
			case KeyEvent.VK_RIGHT:
			{
				if (z != 3) {
					z = 4;
					check();
				}
				break;
			}default:
			{
				
			}
			
			}
			
	
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e)

	{

	}
	public void changePauseState(){
		  paused= !paused;
	}
	public void paint(Graphics g) {

		// ���ػ溯����ʵ��˫�������
		Image offScreenImage = this.createImage(400, 400);
		// ��ý�ȡͼƬ�Ļ���
		Graphics gImage = offScreenImage.getGraphics();
		// ��ȡ�����ĵ�ɫ����ʹ��������ɫ��仭��,���û�����Ч���Ļ����������϶���Ч��
		gImage.setColor(gImage.getColor());
		gImage.fillRect(0, 50, 400, 400);
		// �������һ��ͼ��Ĺ��ܣ��൱��
		gImage.clearRect(0, 50, 400, 400);
		// ���ø�����ػ淽����������ǽ�ȡͼƬ�ϵĻ�������ֹ�ٴ���ײ����ػ�
		super.paint(gImage);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 50, 400, 400);
		g.setColor(Color.pink);
		for (i = 0; i <= length - 1; i++) {
			g.fillRect(m[i], n[i], 10, 10);
		}
		g.setColor(Color.green);
		g.fillRect(objectX, objectY, 10, 10);
		g.setColor(Color.white);
		g.drawString("��ǰ����" + this.life, 300, 60);
		g.drawString("��ǰ�ѳ�ʳ����" + this.foods, 300, 72);
	}
     
	class MyTask extends TimerTask
	{

		@Override
		public void run() {
			
			repaint();
		}
		
		
	}
	
	
}
