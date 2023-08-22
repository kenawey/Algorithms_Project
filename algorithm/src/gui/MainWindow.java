package gui;

import algorithms.DijkstraAlgorithm;
import models.Graph;
import algorithms.Maximum;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JPanel {

    private Graph graph;
    private GraphPanel graphPanel;
    String way="One Shot";
    public MainWindow(){
        super.setLayout(new BorderLayout());
        setGraphPanel();
    }
    
   
 public void seWay(String s){
    way=s;
    }
    public String getway(){return this.way;}

    private void setGraphPanel(){
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(9000, 4096));

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(graphPanel);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(4100, 0));
        add(scroll, BorderLayout.CENTER);
        setTopPanel();
        setButtons();
       
    }

    private void setTopPanel() {
        JLabel info = new JLabel("Graph Control Elements ");
         String[] items = { "One Shot", "Step By Step" };
         String[] statusitems = { "Undirected", "Directed" };
        JComboBox comboBox = new JComboBox( items );              
        comboBox.setBounds(90, 40, 100, 30);  
        
        JComboBox typeBox = new JComboBox( statusitems );              
        typeBox.setBounds(100, 50, 110, 40);  
        
        
        /*****************return here please *****************/
          
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            
         way=""+comboBox.getSelectedItem();
            JOptionPane.showMessageDialog(null,  "you Chose "+comboBox.getSelectedItem());
            }
        });
     
         typeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
           String type= ""+typeBox.getSelectedItem();
           //graph.setType(type);
            JOptionPane.showMessageDialog(null,  "you Chose "+typeBox.getSelectedItem());
            graph.setType(type);
            //Graph G=graph;
            //graph.setType(type);
            graphPanel = new GraphPanel(graph);
            
            
            
            }
        });
     
         JLabel info1 = new JLabel("Graph Type ");
        info1.setForeground(new Color(255, 255, 255));
        
        info.setForeground(new Color(255, 255, 255));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.add(info);
        panel.add(comboBox);
        panel.add(info1);
        panel.add(typeBox);
        
        //panel.add(choose);
       
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(panel, BorderLayout.NORTH);
    }

    private void setButtons(){
        JButton runDijkstra = new JButton("Run Dijkstra");
        setupIcon(runDijkstra, "run");
        
         
    
        JButton runMaximum = new JButton("Run Ford-Fulkerson");
        setupIcon(runMaximum,"run2"); 
        
        JButton reset = new JButton("Clear");
        setupIcon(reset, "reset");
        
        final JButton info = new JButton("How to use it?? ");
        setupIcon(info, "info");

        
        //JTextArea textSize = new JTextArea(5,12);
        //textSize.setBounds(160, 50, 60, 25);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(DrawUtils.parseColor("#AAAAAA"));
        buttonPanel.setBounds(50, 50, 180, 110);
        buttonPanel.add(reset);
        buttonPanel.add(runMaximum);
        buttonPanel.add(runDijkstra);
        //buttonPanel.add(textSize);
        buttonPanel.add(info);
        

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.reset();
            }
        });
        
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Click on empty space to create new node\n" +
                        "Drag from node to node to create an edge\n" +
                        "Click on edges to set the weight\n\n" +
                        "Combinations:\n" +
                        "Shift + Left Click       :    Set node as source\n" +
                        "Shift + Right Click     :    Set node as destination\n" +
                        "Ctrl  + Drag               :    Reposition Node\n" +
                        "Ctrl  + Click                :    Get Path of Node\n" +
                        "Ctrl  + Shift + Click   :    Delete Node/Edge\n");
            }
        });
        
      runMaximum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Maximum full_ford = new Maximum(graph);
                try{
                   // String = full_ford.main(graph) + "the maximum flow is ";
                 JOptionPane.showMessageDialog(null,  full_ford.main(graph));
                    //full_ford.main(graph);
                   // graphPanel.setPath(dijkstraAlgorithm.getDestinationPath());
                } catch (IllegalStateException ise){
                    JOptionPane.showMessageDialog(null, ise.getMessage());
                }
            
            }
        });

        runDijkstra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
                try{
                    dijkstraAlgorithm.run();
                    
                    graphPanel.setPath(dijkstraAlgorithm.getDestinationPath());
                    JOptionPane.showMessageDialog(null,"total weight is "+ dijkstraAlgorithm.getflow(dijkstraAlgorithm.getDestinationPath()));
                } catch (IllegalStateException ise){
                    JOptionPane.showMessageDialog(null, ise.getMessage());
                }
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupIcon(JButton button, String img){
        try {
            Image icon = ImageIO.read(getClass().getResource(
                    "/resources/" + img + ".png"));
            ImageIcon imageIcon = new ImageIcon(icon);
            button.setIcon(imageIcon);
          
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
