/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.MainWindow;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.UIManager;
import gui.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Mohamed_Mostafa
 */


public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {  }

        JFrame j = new JFrame();
        j.setTitle("Algorithms Research");
        j.setSize(new Dimension(900, 600));
        j.add(new MainWindow());
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
