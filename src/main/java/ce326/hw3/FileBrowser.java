package ce326.hw3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileSystemView;
import javax.xml.stream.*;
import javax.xml.namespace.QName;
import javax.xml.stream.events.XMLEvent;


public class FileBrowser {
    
    
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            JFrame frame = new JFrame();
            java.io.File curr_dir = new java.io.File(FileSystemView.getFileSystemView().getHomeDirectory().toString()); // Pernoume to dir tou root
            java.io.File fldr;
            fldr = Paths.get(FileSystemView.getFileSystemView().getHomeDirectory().toString(), ".java-file-browser").toFile();
            JPanel path_links = new JPanel();
            path_links.setSize(0, 0);
            /*
            fvs: Panel agaphmenwn
            search: panel pou tha topothetithei h anazhthsh
            */
            
            
            frame.setSize(new Dimension(1000, 1000));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            
            JPanel fvs = frame_creator(frame, Color.pink, 20, 20, 200, 900, 1000, 1000);
            
            JPanel search_panel = frame_creator(frame, null, 240, 20, 720, 40, 1000, 1000);
            search_panel.setLayout(null);
            JPanel path = frame_creator(frame, null, 240, 62, 720, 30, 1000, 1000);
            path.setBorder(null);
            path.setLayout(new BorderLayout());
            path_links.setLayout(new GridBagLayout());
            JPanel files = frame_creator(frame, Color.LIGHT_GRAY, 240, 100, 720, 900-40-30-8, 1000, 1000); // Sto height afairw ta katallhla kena gia na vgei akrivws sto idio ypsos ta 2 frames
            files.setLayout(null);
            path.add(path_links, BorderLayout.WEST);
            
            JButton searchb = new JButton("Search"); // To buttton ths anazhthshs
            JTextField searchf = new JTextField();
            searchf.setBounds(0, 10, 620 , 20); searchb.setBackground(null);
            searchb.setBounds(630, 10, 100, 20); search_panel.setBorder(null); // Den xreiazetai to Border sthn anazhthsh
            search_panel.add(searchb); search_panel.add(searchf);
            
            frame.addComponentListener(new ComponentAdapter(){
                public void componentResized(ComponentEvent event){
                    searchf.setBounds(0*frame.getWidth()/1000, 10*frame.getHeight()/1000,620*frame.getWidth()/1000, frame.getHeight()*20/1000);
                    searchb.setBounds(630*frame.getWidth()/1000, 10*frame.getHeight()/1000,100*frame.getWidth()/1000, frame.getHeight()*20/1000);

                }
            });
            search_panel.setVisible(false);
            
          JMenu file = new JMenu("File");
          JMenu edit = new JMenu("Edit");
          edit.setEnabled(false);
          JMenu view = new JMenu("View");
          JMenuBar menub = new JMenuBar();
          JMenuItem new_w, exit, copy, paste, cut, delete, rename, favs, properties;
          JCheckBox search, hidden;
          
          new_w = new JMenuItem("New Window");
          copy = new JMenuItem("Copy");
          cut = new JMenuItem("Cut");
          paste = new JMenuItem("Paste");
          paste.setEnabled(false);
          exit = new JMenuItem("Exit");
          delete = new JMenuItem("Delete");
          rename = new JMenuItem("Rename");
          favs = new JMenuItem("Add to Favorites");
          properties = new JMenuItem("Properties");
          
          search = new JCheckBox("Search");
          hidden = new JCheckBox("Hidden Files/Folders");
          
          menub.add(file); // Prosthikh tou menu file sth grammh
          file.add(new_w); file.add(exit); // Prosthikh twn epilogwn
          edit.add(copy); edit.add(paste); edit.add(cut); edit.add(rename); edit.add(delete); edit.add(favs); edit.add(properties);
          view.add(search); view.add(hidden);
          
          Font new_font = new Font("Serif", Font.PLAIN, 18);
                                    
          AffineTransform affinetransform = new AffineTransform();
          FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
          
          JButton button = new JButton();
          button.setBackground(null);
          button.setBorder(null);
          
          button.setOpaque(false);
          
          JPanel links = panel_on_panel(fvs, null, 5, 10, (int)new_font.getStringBounds(FileSystemView.getFileSystemView().getHomeDirectory().getName(), frc).getWidth() + 5, (int)new_font.getStringBounds(FileSystemView.getFileSystemView().getHomeDirectory().getName(), frc).getHeight() + 5, 200, 900);
          JLabel lbl = new JLabel();
          fvs.setLayout(new BorderLayout());
          links.setLayout(new GridLayout(1, 1));
          lbl.setFont(new_font);
          
          lbl.setText(FileSystemView.getFileSystemView().getHomeDirectory().getName());
          lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
          button.add(lbl);
          links.add(button);
          button.setPreferredSize(new Dimension(lbl.getWidth(), lbl.getHeight()));
          button.setContentAreaFilled(false);
          button.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent evt) {
                  if(path.getComponent(0) instanceof JPanel) {
                      if(!(get_path((JPanel)(path.getComponent(0))).equals(FileSystemView.getFileSystemView().getHomeDirectory().toString()))) {
                            files.removeAll();
                            ((JPanel)(path.getComponent(0))).removeAll();
                            folder(FileSystemView.getFileSystemView().getHomeDirectory(), files, (JPanel) path.getComponent(0), hidden.isSelected(), edit);
                      }
                  }
              }
          });
          
          if(fldr.exists()) {
                if(fldr.isDirectory()) {
                    
//                        xml = Paths.get(fldr.getAbsolutePath(), "properties.xml").toFile();
//                        
//                        if(xml.exists()) {
//                        XMLInputFactory input = XMLInputFactory.newInstance();
//                            try {
//                                XMLEventReader rdr = input.createXMLEventReader(new FileReader(xml.toString()));
//                                
//                                while(rdr.hasNext()) {
//                                    XMLEvent fav_folder = rdr.nextEvent();
//                                    
//                                    
//                                    if(fav_folder.isEndDocument())
//                                        continue;
//                                    
//                                    if(fav_folder.isStartDocument())
//                                        continue;
//                                    
//                                    if(fav_folder.toString().charAt(0) != '>'&& fav_folder.toString().charAt(0) != '<') {
//                                    
//                                          button_adder(links, fav_folder, pos, (JPanel) path.getComponent(0), files, hidden, edit);
//                                          links.repaint();
//                                          pos = 0;
//                                    }
//                                }
//                            } catch (FileNotFoundException | XMLStreamException ex) {
//                                Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
                    delete(fldr.toString(), files, path, hidden);
                }
            }
          
          // DHmiourgw ena button, to opoio otan paththei tha phgainei ston antistoixo katalogo

          actions(files, favs, path, hidden, links, rename, delete, properties, copy, cut, paste, edit);

          exit.addMouseListener(new MouseAdapter() { // Dhmiourgia eventListener gia to an patithike to exit
              @Override
              public void mouseReleased(MouseEvent e) {
                  if(SwingUtilities.isLeftMouseButton(e)) {
                    frame.dispose();
                  }
              }
          });
          
          new_w.addMouseListener(new MouseAdapter() { // Dhmiourgia eventListener gia to an patithike to exit
              @Override
              public void mouseReleased(MouseEvent e) {
                  if(SwingUtilities.isLeftMouseButton(e)) {
                    main(null); // Ksanaksekinaei h ektelesh tou parathitrou
                  }
              }
          });
          
                      JPopupMenu popup = new JPopupMenu();
        JMenuItem new_paste = new JMenuItem("Paste");
        new_paste.setEnabled(((JMenuItem)edit.getMenuComponent(1)).isEnabled());
        popup.add(new_paste);
        files.setComponentPopupMenu(popup);
        files.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
               if(SwingUtilities.isRightMouseButton(e)) {
                   if(popup != null)
                    popup.show(files, e.getX(), e.getY());
                   
               }
           } 
        });
 
//          
          search.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  if(search.isSelected()) {
                      search_panel.setVisible(true);
                  }
                  else
                      search_panel.setVisible(false);
              }
          });
          
          
          menub.add(edit); // Prosthikh tou menu edit sth grammh
          menub.add(view); // Prosthikh tou menu view sth grammh
          menub.setBackground(Color.MAGENTA);
          frame.setJMenuBar(menub);
          
          
            folder(curr_dir, files, path_links, false, edit);
             hidden.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  if(hidden.isSelected()) {
                    files.removeAll();
                    
                    
                    File curr_dir = new File(get_path((JPanel)(path.getComponent(0))));
                    ((JPanel)(path.getComponent(0))).removeAll();
                    folder(curr_dir, files, (JPanel)(path.getComponent(0)), true, edit);
                    files.repaint();
                  }
                  else
                  {
                     files.removeAll();
                    
                    
                    File curr_dir = new File(get_path((JPanel)(path.getComponent(0))));
                    ((JPanel)(path.getComponent(0))).removeAll();
                    folder(curr_dir, files, (JPanel)(path.getComponent(0)), false, edit);
                    files.repaint();
                  }
              }
          });
            
            frame.setVisible(true);
            
            searchb.addMouseListener(new MouseAdapter() {
                Thread srch_thrd;
                Thread start;
               public void mouseReleased(MouseEvent e) {
                   
                   
                   if(searchb.getText().equals("Search")) {
                       searching findList = new searching(files, searchf, path, hidden, edit);
                    searchb.setText("Stop");
                    
                    findList.start();
                    start = findList;
                    srch_thrd = new Thread(() -> {
                        try {
                            
                            findList.join();
                            JPanel FileList = findList.searching_files;
                            
                            
                            searchb.setText("Search");
                            files.removeAll();
                            FileList.setSize(files.getWidth() - 30, FileList.getHeight());
                            
                            if(FileList.getHeight() - 5 > files.getHeight()) {
                            JScrollPane scrollFrame = new JScrollPane(findList.searching_files, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                                 scrollFrame.setForeground(null);

                                 scrollFrame.setSize(files.getWidth() - 5, files.getHeight());
                                 scrollFrame.getViewport().getView().setBackground(Color.LIGHT_GRAY);
                                 scrollFrame.getViewport().getView().setForeground(null);
                                 scrollFrame.setBorder(null);
                                 scrollFrame.revalidate();
                                 files.setLayout(new BorderLayout());
                                 files.add(scrollFrame, BorderLayout.CENTER);
                                 
                                 files.addComponentListener(new ComponentAdapter() {
                                    public void componentResized(ComponentEvent e) {
                                            scrollFrame.setSize(files.getWidth(), files.getHeight());
                                            scrollFrame.getComponent(0).setSize(files.getWidth() - 30, scrollFrame.getComponent(0).getHeight());
                                            scrollFrame.revalidate();
                                            scrollFrame.repaint();
                                    } 
                                 });
                                 scrollFrame.revalidate();
                            }
                            
                            else {

                                files.setLayout(null);
                                files.add(FileList, 5, 0);
                            }
                            
                            
                            files.revalidate();
                            files.repaint();
                            start = null;
                        } catch (InterruptedException e1) {
                            System.exit(1);
                        } 
                    });
                    srch_thrd.start();

                   }
                   else {
                       start.interrupt();
                       // Ean den exei diakopei h leitourgia epeidh ginetai load h eikona menw se while-loop
                       // wste na stelnw synexws na teleiwsei
                       while(start.isAlive()) {
                           start.interrupt(); 
                       }
                   }
               }
          });
        }
      });
    }
    
    // H parakatw synarthsh dhmiourgei ta aparaithta panels ta opoia theloume
    private static JPanel frame_creator(JFrame frame, Color clr, int x, int y, int width, int height, int length, int h) {
        JPanel panel = new JPanel();
        
        panel.setBackground(clr);
        panel.setBounds(x, y, width, height);
        panel.setLayout(new BorderLayout());
        
        // Gia to resize tou kathe frame
        frame.addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent event){
                //panel.setSize(new Dimension(width*frame.getWidth()/1000, frame.getHeight()*height/1000));
                panel.setBounds(x*frame.getWidth()/length, y*frame.getHeight()/h,width*frame.getWidth()/length, frame.getHeight()*height/h);
                
            }
        });
        frame.add(panel);
        
        return panel;
    }
    
    private static JPanel panel_on_panel(JPanel frame, Color clr, int x, int y, int width, int height, int panel_length, int panel_height) {
        JPanel panel = new JPanel();
        
        panel.setBackground(clr);
        panel.setBounds(x, y, width, height);
        panel.setLayout(new BorderLayout());
        
        frame.add(panel);
        return panel;
    }
    
    private static void folder(java.io.File curr_dir, JPanel files, JPanel path, boolean hidden, JMenu actions){
        ArrayList<java.io.File> file_names;
        ImageIcon ic;
        JPanel curr_panel;
        final ArrayList<JPanel> panel_list; // Sthn panel list apothikevontai ta panels pou yparxoun
        int x = 0, number_rs = 0, number_cols = 0, i;
        String[] paths = curr_dir.list(); // Ta monopatia tou kathe fakelou
        ArrayList<String> folders, other;
        
        folders = new ArrayList<>();
        other = new ArrayList<>();
        JLabel lb;
        panel_list = new ArrayList<>();
        file_names = new ArrayList<>();
        
        if(paths != null) {
            Arrays.sort(paths);
            actions.setEnabled(false);
                for(String pathn: paths) {
                    if(pathn.charAt(0) != '.') {

                        if(Paths.get(curr_dir.getAbsolutePath(), pathn).toFile().isDirectory()) 
                            folders.add(pathn);
                        else
                            other.add(pathn);
                    }
                    else {
                        if(hidden) {
                            if(Paths.get(curr_dir.getAbsolutePath(), pathn).toFile().isDirectory()) 
                            folders.add(pathn);
                            else
                                other.add(pathn);
                        }
                        else
                            continue;

                    }
                   if(100*x + 60 < files.getWidth()-((Integer)UIManager.get("ScrollBar.width"))) {
                        if(number_rs == 0)
                            number_cols++;
                    }
                    else {
                        number_rs++;
                        x = 0;
                    }
                    x += 1;
                }
        }
        if(paths != null) {
            for(x = 0; x < paths.length; x++)
                paths[x] = null;
            
            for(x = 0; x < folders.size(); x++) {
                paths[x] = folders.get(x);
                
                
            }
        }
            i = 0;
            for(; x < folders.size()+other.size(); x++) {
                paths[x] = other.get(i);
                i++;
            }
            
            x = 0;
            number_rs++;
            
            if(number_cols == 0) {
                Iterator sub_paths = curr_dir.toPath().iterator();
        path_buttons(path, curr_dir.toPath().getRoot(), files, false, actions);
        while(sub_paths.hasNext())
            path_buttons(path, (Path)sub_paths.next(), files, false, actions);
                  
                return;
            }
            
            GridLayout new_layout = new GridLayout(number_rs, number_cols);
            JPanel new_panel = new JPanel();
            new_panel.setBounds(0, 0, number_cols*(100), number_rs*100);
            
            files.setLayout(null);
            new_panel.setLayout(new_layout);
            new_panel.setBackground(null);
            
            if(paths != null) {
                for(String pathnames: paths) {
                    if(pathnames == null)
                        break;

                    if(!hidden && pathnames.charAt(0) == '.')
                        continue;


                    file_names.add(new java.io.File(curr_dir.getAbsoluteFile()+pathnames));

                    if(Paths.get(curr_dir.getAbsolutePath(), pathnames).toFile().isDirectory()) 
                            ic = new ImageIcon("./icons/folder.png");

                    else {
                        if(pathnames.length() < 4)

                            ic = new ImageIcon("./icons/question.png");
                        else {

                                 try(BufferedReader in = new BufferedReader(new FileReader("./icons/"+pathnames.substring(pathnames.length()-3, pathnames.length())+".png"))) {
                                     if(pathnames.charAt(pathnames.length()-4) == '.')
                                        ic = new ImageIcon("./icons/"+pathnames.substring(pathnames.length()-3)+".png");
                                     else
                                         ic = new ImageIcon("./icons/question.png");

                                 } catch(FileNotFoundException ex) {

                                     ic = new ImageIcon("./icons/question.png");
                                 } catch(IOException ex2) {
                                     ic = new ImageIcon("./icons/question.png");
                                 }
                        }
                    }

                    Image image = ic.getImage().getScaledInstance(75, 70, 0);

                    ic = new ImageIcon(image);

                    lb = new JLabel(pathnames, ic, JLabel.LEFT);

                    curr_panel = panel_on_panel(new_panel, null, 0, 0, 100, 110, 100, 110);
                    curr_panel.setVisible(true);


                    x++; // Oi theseis twn stoixeiwn sth lista

                    lb.setVerticalTextPosition(JLabel.BOTTOM);
                    lb.setHorizontalTextPosition(JLabel.CENTER);
                    lb.setVerticalAlignment(JLabel.TOP);
                    lb.setHorizontalAlignment(JLabel.CENTER);
                    lb.setToolTipText(pathnames); ToolTipManager.sharedInstance().setEnabled(true);

                    lb.addMouseListener( new MouseAdapter() {
                                        @Override
                                            public void mouseClicked(MouseEvent e) {

                                                Object src = e.getSource();
                                                JLabel lbl = (JLabel) e.getSource();
                                                JPanel pressed = (JPanel)(((JLabel)src).getParent());
                                               if(SwingUtilities.isRightMouseButton(e)) {
                                                   JPopupMenu popup = new JPopupMenu();
                                                   if(e.getX() - (pressed.getWidth()/2-lbl.getIcon().getIconWidth()/2) < lbl.getIcon().getIconWidth() && e.getY() - (pressed.getHeight()/2-lbl.getIcon().getIconHeight()/2) < lbl.getIcon().getIconHeight()) {
                                                    if(e.getX() > pressed.getWidth()/2-lbl.getIcon().getIconWidth()/2) {
                                                                        JMenuItem copy = (JMenuItem) actions.getMenuComponent(0);
                                                                        JMenuItem paste = (JMenuItem) actions.getMenuComponent(1);
                                                                        JMenuItem cut = (JMenuItem) actions.getMenuComponent(2);
                                                                        JMenuItem rename = (JMenuItem) actions.getMenuComponent(3);
                                                                        JMenuItem delete = (JMenuItem) actions.getMenuComponent(4);
                                                                        JMenuItem fvs = (JMenuItem) actions.getMenuComponent(5);
                                                                        JMenuItem properties = (JMenuItem) actions.getMenuComponent(6);

                                                                        popup.add(copy);   popup.add(paste); popup.add(cut); popup.add(rename); 
                                                                        popup.add(delete); popup.add(fvs); popup.add(properties); 

                                                                         popup.show(((JLabel) src), e.getX(), e.getY());

                                                                         popup.addPopupMenuListener(new PopupMenuListener() {
                                                                             @Override
                                                                             public void popupMenuCanceled(PopupMenuEvent e) {

                                                                             }

                                                                             @Override
                                                                             public void  popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                                                                                 if(actions.getMenuComponentCount() == 0) {
                                                                                     actions.add(copy);
                                                                                     actions.add(paste);
                                                                                     actions.add(cut);
                                                                                     actions.add(rename);
                                                                                     actions.add(delete);
                                                                                     actions.add(fvs);
                                                                                     actions.add(properties);
                                                                                 }

                                                                                     //edit_add(actions, copy,  paste,  cut,  rename,  delete,  favs,  properties);
                                                                             }

                                                                             @Override
                                                                             public void  popupMenuWillBecomeVisible(PopupMenuEvent e) {

                                                                             }
                                                                         });
                                                        }
                                                        else {
                                                            popup = files.getComponentPopupMenu();
                                                            popup.show(((JLabel) src), e.getX(), e.getY());

                                                            popup.addPopupMenuListener(new PopupMenuListener() {
                                                                             @Override
                                                                             public void popupMenuCanceled(PopupMenuEvent e) {

                                                                             }

                                                                             @Override
                                                                             public void  popupMenuWillBecomeInvisible(PopupMenuEvent e) {


                                                                                     //edit_add(actions, copy,  paste,  cut,  rename,  delete,  favs,  properties);
                                                                             }

                                                                             @Override
                                                                             public void  popupMenuWillBecomeVisible(PopupMenuEvent e) {

                                                                             }
                                                                         });

                                                        }

                                                   }

                                                   else {
                                                        popup = files.getComponentPopupMenu();
                                                        popup.show(((JLabel) src), e.getX(), e.getY());

                                                            popup.addPopupMenuListener(new PopupMenuListener() {
                                                                             @Override
                                                                             public void popupMenuCanceled(PopupMenuEvent e) {

                                                                             }

                                                                             @Override
                                                                             public void  popupMenuWillBecomeInvisible(PopupMenuEvent e) {


                                                                                     //edit_add(actions, copy,  paste,  cut,  rename,  delete,  favs,  properties);
                                                                             }

                                                                             @Override
                                                                             public void  popupMenuWillBecomeVisible(PopupMenuEvent e) {

                                                                             }
                                                                         });

                                                        }


                                               }




                                                checked_creator( pressed,  e,  hidden, actions);

                                            }
                    });

                    lb.setPreferredSize(new Dimension(40, 90));
                    curr_panel.add(lb);
                    panel_list.add(curr_panel);

                    lb.addMouseMotionListener(new MouseAdapter() {
                       public void mouseMoved(MouseEvent e) {
                           Object src = e.getSource();
                           JPanel pressed;
                           JLabel lbl;

                           if(src instanceof JLabel) {
                               pressed = (JPanel)(((JLabel) src).getParent());
                               lbl = (JLabel)pressed.getComponent(0);

                               if(e.getX() - (pressed.getWidth()/2-lbl.getIcon().getIconWidth()/2) < lbl.getIcon().getIconWidth() && e.getY() - (pressed.getHeight()/2-lbl.getIcon().getIconHeight()/2) < lbl.getIcon().getIconHeight()) {
                                   if(e.getX() > pressed.getWidth()/2-lbl.getIcon().getIconWidth()/2) {

                                        ToolTipManager.sharedInstance().setEnabled(true);

                                   }
                                   else {
                                       ToolTipManager.sharedInstance().setEnabled(false);
                                    }
                           }
                               else { ToolTipManager.sharedInstance().setEnabled(false);
                                    }
                           }
    //                       
                       } 
                    });



            }
            }
        JScrollPane scrollFrame = new JScrollPane(new_panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        if(number_rs*100 >= files.getHeight()) {
                                scrollFrame.setSize(files.getWidth(), files.getHeight());
        }
        else {
             if(number_cols*100 < files.getWidth())
                scrollFrame.setSize(number_cols*100, number_rs*100);
                   else
                       scrollFrame.setSize(files.getWidth(), number_rs*100);
        }
        files.setLayout(null);
        scrollFrame.setForeground(null);
        scrollFrame.getViewport().getView().setBackground(Color.LIGHT_GRAY);
        scrollFrame.getViewport().getView().setForeground(null);
        scrollFrame.setBorder(null);
        scrollFrame.revalidate();
        
        files.add(scrollFrame);
        files.repaint();
        for (ComponentListener componentListener : files.getComponentListeners()) {
            files.removeComponentListener(componentListener);
        }
        // Topothetw component listener wste ean yparxei allagh sto megethos tou parathirou na allaksei kai to jscroll pane
        files.addComponentListener(new ComponentAdapter(){
                @Override
                public void componentResized(ComponentEvent event){
                    JScrollPane pane;
                    

                    int x = 0;
                    int number_rs = 0; int number_cols = 0;
                    int num_els = 0;
                    
                    // Mesw ths while ypologizoume to neo arithmo grammwn kai sthlwn sto layout
                    while(num_els < new_panel.getComponentCount()) {
                    //panel.setSize(new Dimension(width*frame.getWidth()/1000, frame.getHeight()*height/1000));
                        if(100*x + 60 < files.getWidth()-((Integer)UIManager.get("ScrollBar.width"))) {
                            if(number_rs == 0)
                                number_cols++;
                        }
                        else {
                            number_rs++;
                            x = 0;
                        }
                        num_els++;
                        x++;
                    }
                    if(number_cols == 0)
                        return;
                    
                    number_rs++;
                    
                    if(files.getComponentAt(0, 0) instanceof JScrollPane) {
                        pane = (JScrollPane) files.getComponentAt(0, 0);
                        
                        if(pane.getComponentAt(0, 0).getComponentAt(0, 0) instanceof JPanel) { // Lamvanw to panel katw apo to scroll Frame
                            
                            if(number_rs*100 > files.getHeight()) {
                                pane.setSize(files.getWidth(), files.getHeight());
                            }
                            else {
                                if(number_cols*100 < files.getWidth())
                                    pane.setSize(number_cols*100, number_rs*100);
                                else
                                    pane.setSize(files.getWidth(), number_rs*100);
                            }
                        }
                        
                        pane.revalidate();
                        files.repaint();
                    }
                    
                    new_panel.setLayout(new GridLayout(number_rs, number_cols));
                    new_panel.repaint();
                    
                }
        });
        
        JLabel visible_path = new JLabel();
        Iterator sub_paths = curr_dir.toPath().iterator();
        path_buttons(path, curr_dir.toPath().getRoot(), files, false, actions);
        while(sub_paths.hasNext())
            path_buttons(path, (Path)sub_paths.next(), files, false, actions);
        visible_path.setFont(new Font("Serif", Font.PLAIN, 25));
        files.repaint();
        
        
        
    }
    
    private static void checked_creator(Object src, MouseEvent e, boolean hidden, JMenu edit) {
        JLabel lbl;

        
        if(src instanceof JPanel) {
                           JPanel pressed = (JPanel)src;
                           
                           if(pressed.getComponent(0) instanceof JLabel) {
                                lbl = (JLabel)pressed.getComponent(0);
                               JPanel p2;
                              
                           if(e.getX() - (pressed.getWidth()/2-lbl.getIcon().getIconWidth()/2) < lbl.getIcon().getIconWidth() && e.getY() - (pressed.getHeight()/2-lbl.getIcon().getIconHeight()/2) < lbl.getIcon().getIconHeight()) {
                               if(e.getX() > pressed.getWidth()/2-lbl.getIcon().getIconWidth()/2) {
                                    for(int i = 0; i < ((JPanel)src).getParent().getComponentCount(); i++) {
                                   p2 = (JPanel)(((JPanel)src)).getParent().getComponent(i);
                                   if(p2.getComponent(0) instanceof JLabel) {
                                       
                                       if(p2.getComponent(0).getComponentAt(p2.getWidth()/2-lbl.getIcon().getIconWidth()/2, 0) instanceof JPanel) {
                                       
                                           if(p2.getComponentCount() == 1) {
                                                ((JLabel)p2.getComponent(0)).removeAll();
                                               ((JLabel)p2.getComponent(0)).repaint(); 
                                        }
                                       }
                                       
                                   }
                                   
                               }
                           //  elegxos gia to an h thesh touy deikth vrisketai sto eikonisdio
                           
                                    JPanel new_panel = new JPanel();
                                    
                                    
                                    new_panel.setBounds(0, 0 , lbl.getParent().getWidth(), lbl.getParent().getHeight());
                                    new_panel.setBackground(null);
                                    new_panel.setForeground(null);
                                    new_panel.setOpaque(false);
                                    
                                    new_panel.setBorder(BorderFactory.createLineBorder(Color.gray));
                                    lbl.add(new_panel);
                                    new_panel.repaint();
                                    
                                    ((JPanel)src).addComponentListener(new ComponentAdapter(){ // An allaksei megethos to label allazei kai h epilogh thesh
                                         public void componentResized(ComponentEvent event){
                                             if(lbl.getComponentCount() >= 1) {
                                                 
                                                new_panel.setBounds(0, 0 , lbl.getParent().getWidth(), lbl.getParent().getHeight());
                                                new_panel.repaint();
                                                
                                             }
                                         }
                                     });
//                                    
                                    
                                    
                                    new_panel.addMouseMotionListener(new MouseAdapter() {
                                         public void mouseMoved(MouseEvent e) {
                                             new_panel.setToolTipText(lbl.getText());
                                    ToolTipManager.sharedInstance().setEnabled(true);
                                         }
                                    });
                                    edit.setEnabled(true);
                                    new_panel.addMouseListener( new MouseAdapter() {
                                     @Override
                                     public void mouseReleased(MouseEvent e1) {
                                         Object src = e1.getSource();
                                         if(src instanceof JPanel) {
                                             if(SwingUtilities.isRightMouseButton(e1)) {
                                                 JPopupMenu popup = new JPopupMenu();
                                                 JMenuItem copy = (JMenuItem) edit.getMenuComponent(0);
                                                 JMenuItem paste = (JMenuItem) edit.getMenuComponent(1);
                                                 JMenuItem cut = (JMenuItem) edit.getMenuComponent(2);
                                                 JMenuItem rename = (JMenuItem) edit.getMenuComponent(3);
                                                 JMenuItem delete = (JMenuItem) edit.getMenuComponent(4);
                                                 JMenuItem fvs = (JMenuItem) edit.getMenuComponent(5);
                                                 JMenuItem properties = (JMenuItem) edit.getMenuComponent(6);
                                                 
                                                 popup.add(copy); popup.add(paste); popup.add(cut); popup.add(rename);
                                                 popup.add(delete); popup.add(fvs); popup.add(properties);
                                                 
                                                  popup.show(((JPanel) src), e.getX(), e.getY());
                                                 popup.addPopupMenuListener(new PopupMenuListener() {
                                                    @Override
                                                     public void popupMenuCanceled(PopupMenuEvent e) {

                                                     }

                                                        @Override
                                                        public void  popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                                                                             if(edit.getMenuComponentCount() == 0) {
                                                                                 edit.add(copy);
                                                                                 edit.add(paste);
                                                                                 edit.add(cut);
                                                                                 edit.add(rename);
                                                                                 edit.add(delete);
                                                                                 edit.add(fvs);
                                                                                 edit.add(properties);
                                                                             }
                                                                                 //edit_add(actions, copy,  paste,  cut,  rename,  delete,  favs,  properties);
                                                                         }

                                                                         @Override
                                                                         public void  popupMenuWillBecomeVisible(PopupMenuEvent e) {

                                                                         }
                                                                     });
                                             }
                                             else {
                                                JPanel files;
                                                JLabel label = (JLabel) ((JPanel) ((JPanel) src).getParent().getParent()).getComponentAt(0, 0); // Me afth edw thn entolh pairnw to onoma tou arxeiou to opoio dialekse o xrhsths
                                                String relative_name = label.getText();
                                                JFrame frame = (JFrame)SwingUtilities.windowForComponent((JPanel) src); // To frame sto opoio periexontai ola ta antikeimena

                                                JPanel path = (JPanel) ((JPanel) (label).getParent().getParent().getParent().getParent().getParent().getParent()).getComponentAt(240*frame.getWidth()/1000, 62*frame.getHeight()/1000);
                                                String absolute_path = get_path((JPanel)(path.getComponent(0)));//((JLabel)path.getComponentAt(0, 0)).getText(); // Absolute_path: o 

                                                if(Paths.get(absolute_path, relative_name).toFile().isDirectory()) {
                                                    java.io.File checked_dir = Paths.get(absolute_path, relative_name).toFile(); //new java.io.File(absolute_path+"/"+relative_name);
                                                    if(checked_dir.canRead()) {
                                                        ((JPanel)(path.getComponent(0))).removeAll();
                                                        files = (JPanel) ((JPanel) (label).getParent().getParent().getParent().getParent().getParent());
                                                        files.removeAll();
                                                        ((JPanel)(path.getComponent(0))).removeAll();
                                                        folder(checked_dir, files, (JPanel)(path.getComponent(0)), hidden, edit);
                                                        frame.repaint();
                                                    }


                                                 else {
                                                     javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                                         public void run() {

                                                             JPanel panel = new JPanel(new BorderLayout());
                                                             panel.setVisible(true);
                                                             JOptionPane.showMessageDialog(panel, "Permission denied");
                                                         }
                                                     });
                                                 }

                                             }

                                             else {
                                                 if(Desktop.isDesktopSupported()) {
                                                     try {
                                                            Desktop.getDesktop().open(Paths.get(absolute_path, relative_name).toFile());
                                                     } catch (IOException ex) {
                                                        
                                                         
                                                         javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                                         public void run() {
                                                             JPanel panel = new JPanel(new BorderLayout());
                                                             panel.setVisible(true);
                                                             JOptionPane.showMessageDialog(panel, "Cannot run");
                                                         }
                                                     });
                                                     }
                                                 }
                                             }
                                         }
                                     }
                                     }
                                     
                                    });   
                                    }
                            }
                        }
                       }
        
    }
    // Orisma to files kai to path epistrefei to plhres onoma tou shmademenou arxeiou
    private static String copy(JPanel files, JLabel path) {
        String input_path = null;
        if(files.getComponent(0) instanceof JScrollPane) {
            JScrollPane pane = (JScrollPane)files.getComponent(0);
            JPanel panel = (JPanel)((JViewport)pane.getComponent(0)).getComponent(0);
            
                for(int i = 0; i < panel.getComponentCount(); i++) {
                    
                    for (MouseListener mouseListener : ((JLabel)(((JPanel)(panel.getComponent(i))).getComponent(0))).getMouseListeners()) {
                        JLabel lbl = (JLabel)panel.getComponent(i).getComponentAt((100-75)/2, 0);
                        if(panel.getComponent(i).getComponentAt((100-75)/2, 0) instanceof JLabel) {
                                
                                // Topothetw ena aorato panel me to opoio argotera tha elegxw an exei oristei antigrafh
                                if(lbl.getComponentCount() >= 1) {
                                    
                                    if(lbl.getComponent(0).isVisible()) {
                                    
                                        JPanel cpy = new JPanel();
                                        
                                        cpy.setVisible(false);
                                        cpy.setBounds(0, 0, 0, 0);
                                        lbl.add(cpy);
                                        
                                        input_path = Paths.get(path.getText(),  lbl.getText()).toString();
                                        
                                    }
                                    else {
                                        lbl.removeAll();
                                    }
                                        
                                }
                         }
                    }
               }
        }
        
        return input_path;
    }
    // EPistrefei to run thread wste o kalwn na perimenei prin kanei diagrafh
    private static Thread copy_file(Path dst, Path copied_file, JPanel path, JPanel files, JCheckBox hidden, boolean delete, JMenu edit) {
        
       Thread basic_thrd = null;
        try {
            java.io.File copyf = new java.io.File(copied_file.toString());
                
                if(dst.toFile().exists()) {
                    
                    if(new java.io.File(dst.toString()).isDirectory() && copied_file.toFile().isDirectory()) {
                        
                        // Mbasic_thrd... Perimenw na teleiwsei afth gia nea emfanish parathirou
                        basic_thrd = new Thread(() -> {
                            JFrame fr = new JFrame();
                                                fr.setSize(100, 100);
                                    int response = JOptionPane.showConfirmDialog(fr,"Write on folder: " + dst.toString() +"?");
                                    Thread prev_thread = null;
                                    if(response == JOptionPane.YES_OPTION) {
                                        
                                        for(String pathn: copyf.list()) {
                                            JPanel new_path = new JPanel();
                                            JLabel visible_path = new JLabel(dst.toString());
                                            new_path.add(visible_path);
                                            prev_thread = copy_file((Path)(Paths.get(((JLabel)(new_path).getComponent(0)).getText(), pathn)) , (Path)(Paths.get(copied_file.toString(), pathn)), new_path, files, hidden, delete, edit);
                                        }
                                        
                                        if(prev_thread != null)
                                                try {
                                                    prev_thread.join();
                                                    if(delete) {
                                                        delete(copied_file.toString(), files, path, hidden);

                                                        files.removeAll();
                                                            File curr_dir = new java.io.File(get_path((JPanel) path.getComponent(0)));
                                                            ((JPanel) path.getComponent(0)).removeAll();
                                                            folder(curr_dir, files, (JPanel) path.getComponent(0), hidden.isSelected(), edit);
                                                            files.repaint();
                                                    }
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        
                                                        
                                                        
                                    }
                        });
                        basic_thrd.start();
                        
                    }
                    else {
                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                JFrame fr = new JFrame();
                                                fr.setSize(100, 100);
                                                
                                    int response = JOptionPane.showConfirmDialog(fr,"Replace file: " + dst.toString() +"?");
                                    
                                    if(response == JOptionPane.YES_OPTION) {
                                                        
                                                        delete(dst.toString(), files, path, hidden);
                                                        
                                                        copy_file(dst, copied_file, path, files, hidden, delete, edit);
                                                        
                                                        
                                                        
                                                        if(delete) {
                                                            delete(copied_file.toString(), files, path, hidden);
                                                            
                                                            files.removeAll();
                                                            File curr_dir = new java.io.File(get_path((JPanel)( path.getComponent(0))));
                                                            ((JPanel) path.getComponent(0)).removeAll();
                                                            folder(curr_dir, files, (JPanel) path.getComponent(0), hidden.isSelected(), edit);
                                                            files.repaint();
                                                        }   
                                                        
                                                   
                                    }
                                    
                                    
                                        }
                                    });
                    }
                    
                }
                else {
                    
                    Files.copy(copied_file, dst);
                    if(new java.io.File(dst.toString()).isDirectory()) {
                        for(String pathn: copyf.list()) {
                            JPanel new_path = new JPanel();
                            JLabel visible_path = new JLabel(dst.toString());
                            new_path.add(visible_path);


                                copy_file((Path)(Paths.get(((JLabel)(new_path).getComponent(0)).getText(), pathn)) , (Path)(Paths.get(copied_file.toString(), pathn)), new_path, files, hidden, delete, edit);
                               
                            
                            
                            

                        }
                    }
                    if(delete) {
                                delete(copied_file.toString(), files, path, hidden);
                    }
                    
                }
                
                
                
                                  } catch(FileNotFoundException ex) {
                                      javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                JPanel panel = new JPanel(new BorderLayout());
                                                panel.setVisible(true);
                                                JOptionPane.showMessageDialog(panel, "Could not copy file " + copied_file.toString());
                                        }
                                    });
                                  }
        
                                catch (IOException  ex) {
                                      javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                JPanel panel = new JPanel(new BorderLayout());
                                                panel.setVisible(true);
                                                JOptionPane.showMessageDialog(panel, "Could not copy file " + copied_file.toString());
                                        }
                                    });
                                      
                                  } 
        return basic_thrd;
    }
    
    private static void delete(String checked, JPanel files, JPanel path, JCheckBox hidden) {
        
        
        try {
            if(!Paths.get(checked).toFile().exists())
                return;
            if(!Paths.get(checked).toFile().isDirectory())
                Files.delete((Path)Paths.get(checked));
            else {
                for(String s: Paths.get(checked).toFile().list()) {
                    java.io.File currentFile = Paths.get(checked, s).toFile();
                    if(currentFile.isDirectory())
                        delete(Paths.get(checked, s).toString(), files, path, hidden);
                    currentFile.delete();
                    
                }
                Files.delete((Path)Paths.get(checked));
                
        }
            
                      } catch (IOException ex) {
                          javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                JPanel panel = new JPanel(new BorderLayout());
                                                panel.setVisible(true);
                                                JOptionPane.showMessageDialog(panel, "Could not delete file " + checked);
                                        }
                                    });
                      }
    }
    
    private static boolean paste(JPanel files, JPanel path, JCheckBox hidden, boolean delete, String copied_file, JMenu edit) {
        JPanel panel;
        JLabel lbl, dst_lbl = null;
        Path dst = null;
        
        if(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0) instanceof JPanel) {
        for(int i = 0; i <(((JPanel)(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0))).getComponentCount()); i++) {
                                      
                                      panel = ((JPanel)(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0)));
                                      lbl = (JLabel)(panel.getComponent(i).getComponentAt(0, 0));
                                      
                                      if(lbl.getComponentCount() >= 1 ) {
                                          if(lbl.getComponent(0).isVisible()) {
                                              dst_lbl = lbl; // Sto dst lbl apothikevetai h timh proorismou
                                            
                                          }
                                          
                                      }
                                      
                                  }
        }
        
                                  if(copied_file != null && dst_lbl != null) {
                                          dst = Paths.get(get_path((JPanel)path.getComponent(0)), dst_lbl.getText(), Paths.get(copied_file).getFileName().toString());
                                          
                                          // Ean o proorismos einai o idios den tou epitrepw na synexisei
                                          if(!dst.getParent().equals(Paths.get(copied_file))) 
                                              copy_file(dst, Paths.get(copied_file), path, files, hidden, delete, edit);
                                          
                                          
                                          return true;
                                  }
                                  else {
                                    return false;
                                  }
                                      
    }
    
    private static long folder_size(Path folder) {
        long size = 0;
        if(folder.toFile().isDirectory()) {
            for(String pathn : folder.toFile().list()) {
                if(Paths.get(folder.toFile().getAbsolutePath(), pathn).toFile().isDirectory())
                    size += folder_size(Paths.get(folder.toFile().getAbsolutePath(), pathn));
                else
                    size += Paths.get(folder.toFile().getAbsolutePath(), pathn).toFile().length();
            }
        }
        else
            size = folder.toFile().length();
        return size;
    }
    
   // Dhmiourgei ta buttons kai ta prosthetei sto panel twn links 
    
    private static void button_adder(JPanel links, XMLEvent fav_folder, int pos, JPanel path, JPanel files, JCheckBox hidden, JMenu edit) {
        JLabel name = null;
        JButton path_button;
        final String full_path;
        
        Font new_font = new Font("Serif", Font.PLAIN, 18);
                                    
          AffineTransform affinetransform = new AffineTransform();
          FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        for(int i = 0; i < fav_folder.toString().length(); i++) {
                                            if(fav_folder.toString().charAt(i) == '\"') {
                                                if(pos == 0) {
                                                    pos = i;
                                                }
                                                else {
                                                    if(name == null) {
                                                        name = new JLabel();
                                                        name.setText(fav_folder.toString().substring(pos + 1, i));
                                                        pos = i;
                                                        
                                                    }
                                                    else {
                                                        
                                                        for(int j = i + 1; j < fav_folder.toString().length(); j++) {
                                                            if(fav_folder.toString().charAt(j) == '\"') {
                                                                pos = j;
                                                                break;
                                                            }
                                                        }
                                                        
                                                        full_path = fav_folder.toString().substring(i+1, pos);
                                                        
                                                        path_button = new JButton();
                                                        path_button.setBackground(null);
                                                        path_button.setBorder(null);
                                                        path_button.setOpaque(false);
                                                        
                                                        
                                                        if(links.getWidth() > (int)new_font.getStringBounds(name.getText(), frc).getWidth())
                                                            links.setSize(links.getWidth() , links.getHeight() + (int)new_font.getStringBounds(name.getText(), frc).getHeight()+ 2);
                                                        else
                                                            links.setSize((int)new_font.getStringBounds(name.getText(), frc).getWidth() + 3, links.getHeight() + (int)new_font.getStringBounds(name.getText(), frc).getHeight()+ 2);
                                                        links.setLayout(new GridLayout(((GridLayout)links.getLayout()).getRows() + 1, ((GridLayout)links.getLayout()).getColumns()));
                                                        
                                                        name.setFont(new_font);
                                                        
                                                        
                                                        name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));path_button.setPreferredSize(new Dimension(name.getWidth(), name.getHeight()));
                                                        path_button.setVisible(true);
                                                        name.setVisible(true);
                                                        path_button.setContentAreaFilled(false);
                                                        links.revalidate();
                                                        name.addMouseListener(new MouseAdapter() {
                                                            public void mouseClicked(MouseEvent evt) {
                                                                if(SwingUtilities.isRightMouseButton(evt)) {
                                                                    
                                                                    JPopupMenu menu = new JPopupMenu();
                                                                    JMenuItem delete = new JMenuItem("delete");
                                                                    menu.add(delete);
                                                                    menu.show((JLabel)(evt.getSource()), evt.getX(), evt.getY());
                                                                    
                                                                    delete.addActionListener(new ActionListener() {
                                                                        public void actionPerformed(ActionEvent e) {
                                                                            Path tmp_file;
                                                                            int button_pos = 0, counter = 0;
                                                                            
                                                                            try {
                                                                                
                                                                                for(int j = 0; j < links.getComponentCount(); j++) {
                                                                                    
                                                                                    if(links.getComponent(j) == path_button) {
                                                                                        links.remove(j);
                                                                                        links.setLayout((new GridLayout(((GridLayout)links.getLayout()).getRows(), 1)));
                                                                                        
                                                                                        button_pos = j - 1;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                                
                                                                                java.io.File xml = Paths.get(FileSystemView.getFileSystemView().getHomeDirectory().toString(), ".java-file-browser", "properties.xml").toFile();
                                                                                tmp_file = Files.createTempFile(null, null);
                                                                                XMLOutputFactory output = XMLOutputFactory.newInstance();
                                                                                XMLInputFactory input = XMLInputFactory.newInstance();
                                                                            XMLEventReader rdr = input.createXMLEventReader(new FileReader(xml.toString()));

                                                                            XMLEventWriter writer = output.createXMLEventWriter((new FileOutputStream(tmp_file.toFile())));
                                                                            
                                                                             XMLEventFactory event = XMLEventFactory.newInstance();
                                                                             XMLEvent fav_folder;
                                                                             if(button_pos == 0) {
                                                                                 
                                                                             }
                                                                             
                                                                            while(rdr.hasNext()) {
                                                                                
                                                                                
                                                                                fav_folder  = rdr.nextEvent();
                                                                                if(button_pos != 0) {
                                                                                    if(counter == button_pos && fav_folder.toString().charAt(0) == 'd') {
                                                                                        counter++;
                                                                                        continue;
                                                                                    }

                                                                                    if(fav_folder.toString().charAt(0) == 'd')
                                                                                        counter++;

                                                                                    if(fav_folder.toString().charAt(0) == '<' && counter == button_pos)
                                                                                        continue;

                                                                                    if(fav_folder.toString().charAt(0) == '>' && counter == button_pos + 1) {
                                                                                        counter++;
                                                                                        continue;
                                                                                    }
                                                                                }
                                                                                
                                                                                else {
                                                                                    
                                                                                    if(fav_folder.toString().charAt(0) == 'd')
                                                                                        continue;
                                                                                    
                                                                                    if((fav_folder.toString().charAt(0) == '<' || fav_folder.toString().charAt(0) == '>' || fav_folder.toString().charAt(0) == '\n') && fav_folder.toString().length() == 1)
                                                                                        continue;
                                                                                }
                                                                                
                                                                                
                                                                                 writer.add(fav_folder);
                                                                                 

                                                                          }
                                                                          Files.move(tmp_file, Paths.get(FileSystemView.getFileSystemView().getHomeDirectory().toString(), ".java-file-browser", "properties.xml"), StandardCopyOption.REPLACE_EXISTING);
                                                                            writer.close();
                                                                            links.revalidate();
                                                                                        links.repaint();
                                                                            } catch (IOException | XMLStreamException ex) {
                                                                                Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                                                                            }
                                                                            
                                                                            
                                                                        }
                                                                    });
                                                                   
                                                                }
                                                                else {
                                                                    if(path.getComponent(0) instanceof JLabel) {

                                                                        if(!(((JLabel)(path.getComponent(0))).getText().equals(full_path))) {
                                                                              files.removeAll();
                                                                              path.removeAll();
                                                                              folder(new java.io.File(full_path), files, path, hidden.isSelected(), edit);
                                                                              files.repaint();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        });
                                                        
                                                        
                                                        path_button.add(name);
                                                        links.add(path_button);
                                                        break;
                                                        
                                                    }
                                                }
                                                    
                                            }
                                        }
        
    }
    
    private static void path_buttons(JPanel path, Path added_path, JPanel files, boolean hidden, JMenu edit) {
        JLabel name = new JLabel(), sep = new JLabel();
        name.setText(added_path.toString());
        
        Font new_font = new Font("Serif", Font.PLAIN, 20);
        sep.setText("<");
        
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        
        if(added_path.toString().length() != 1 || !(added_path).equals(FileSystemView.getFileSystemView().getRoots()[0].toPath()))
            path.setSize(path.getWidth() + (int)new_font.getStringBounds(added_path.toString(), frc).getWidth() + (int)new_font.getStringBounds("<", frc).getWidth() + 5, path.getHeight());
        else
            path.setSize(path.getWidth() + (int)new_font.getStringBounds(added_path.toString(), frc).getWidth() + 5, path.getHeight());
        
        // Prosthikh tou seperator mono ean to monopati den einai to root
        if(!(added_path).equals(FileSystemView.getFileSystemView().getRoots()[0].toPath().getRoot()))
            path.add(sep);
        
        path.add(name);
        name.setFont(new_font); sep.setFont(new_font);
        name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        name.setSize(new Dimension((int)new_font.getStringBounds(added_path.toString(), frc).getWidth(), 0));
        name.setVisible(true);
        path.revalidate();
        name.addMouseListener(new MouseAdapter() {
             public void mouseClicked(MouseEvent evt) {
                 Object src = evt.getSource();
                 Path new_path = null;
                 
                 for(int i = 0; i < path.getComponentCount(); i++) {
                     if(path.getComponent(i) instanceof JLabel) {
                         
                         if(((JLabel)(path.getComponent(i))).getText().equals("<"))
                             continue;
                         
                         
                         
                         if(new_path != null)
                            new_path = Paths.get(new_path.toString(), ((JLabel)(path.getComponent(i))).getText());
                         else
                             new_path = Paths.get(((JLabel)(path.getComponent(i))).getText());
                         
                         
                         if(path.getComponent(i).equals(src)) {
                             path.setSize(0, 0);
                             
                             
                             break;
                         }
                        
                         
                     }
                 }
                 
                 if(new_path != null) {
                     path.removeAll();
                     files.removeAll();
                     JMenuBar menu = ((JFrame)SwingUtilities.windowForComponent(path)).getJMenuBar();
                     
                     
                    folder(new_path.toFile(), files, path, ((JCheckBox)(((JMenu)(menu.getMenu(2))).getMenuComponent(1))).isSelected(), edit);
                    path.revalidate();
                 }
             }
        });
    }
    
    // Lhpsh tou path
    private static String get_path(JPanel path) {
        Path new_path = null;
        for(int i = 0; i < path.getComponentCount(); i++) {
                     if(path.getComponent(i) instanceof JLabel) {
                         if(((JLabel)(path.getComponent(i))).getText().equals("<"))
                             continue;
                         
                         
                         if(new_path != null)
                            new_path = Paths.get(new_path.toString(), ((JLabel)(path.getComponent(i))).getText());
                         else
                             new_path = Paths.get(((JLabel)(path.getComponent(i))).getText());
                         
                     }
                 }
        
        if(new_path != null)
            return(new_path.toString());
        
        else
            return null;
    }
    
    private static void actions(JPanel files, JMenuItem favs, JPanel path, JCheckBox hidden, JPanel links, JMenuItem rename, JMenuItem delete, JMenuItem properties, JMenuItem copy, JMenuItem cut, JMenuItem paste, JMenu edit) {
        favs.addMouseListener(new MouseAdapter() {
              public void mouseReleased(MouseEvent e) {
                  java.io.File fldr, xml;
                  fldr = Paths.get(FileSystemView.getFileSystemView().getHomeDirectory().toString(), ".java-file-browser").toFile();
                  JPanel whole_panel;
                  
                  JLabel lbl;
                      if(fldr.exists()) {
                          if(fldr.isFile()) {
                              delete(fldr.getAbsolutePath(), files, path, hidden);
                              fldr.mkdir();
                              fldr.setReadOnly();
                              xml = Paths.get(fldr.getAbsolutePath(), "properties.xml").toFile();
                              try {
                              xml.createNewFile();
                              
                              XMLOutputFactory output = XMLOutputFactory.newInstance();
                              XMLEventWriter writer = output.createXMLEventWriter((new FileOutputStream(xml)));
                              
                              XMLEventFactory event = XMLEventFactory.newInstance();
                              writer.add(event.createStartDocument("UTF-8", "1.0"));
                              writer.add(event.createStartElement(new QName("favorites"), null, null));
                              
                              for(int i = 0; i < ((JPanel)((JViewport)(((JScrollPane)files.getComponent(0)).getComponent(0))).getComponent(0)).getComponentCount(); i++) {
                                  whole_panel = (JPanel)((JViewport)(((JScrollPane)files.getComponent(0)).getComponent(0))).getComponent(0);
                                  
                                  if(((JPanel) whole_panel.getComponent(i)).getComponent(0) instanceof JLabel) {
                                      lbl = (JLabel)(((JPanel) whole_panel.getComponent(i)).getComponent(0));
                                      for(int j = 0; j < lbl.getComponentCount(); j++) {
                                          if(lbl.getComponent(j).isVisible()) {
                                              XMLEvent sampleElement = event.createCharacters("<directory name=\"" + lbl.getText() +"\"" + " path=" + "\"" + Paths.get(((JLabel)path.getComponent(0)).getText(), lbl.getText()) +"\"/>");
                                              writer.add(sampleElement);

                                              writer.add(event.createEndDocument());
                                              writer.flush();
                                          }
                                      }
                                    }
                              }
                              writer.close();                            
                           
                          } catch (IOException | XMLStreamException  ex) {
                              Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                          }
                              
                          }
                          else {
                              
                              XMLInputFactory input = XMLInputFactory.newInstance();
                              xml = Paths.get(fldr.getAbsolutePath(), "properties.xml").toFile();
                              
                              try {
                                Path tmp_file = Files.createTempFile(null, null);
                                XMLOutputFactory output = XMLOutputFactory.newInstance();
                                XMLEventReader rdr = input.createXMLEventReader(new FileReader(xml.toString()));

                                XMLEventWriter writer = output.createXMLEventWriter((new FileOutputStream(tmp_file.toFile())));

                                 XMLEventFactory event = XMLEventFactory.newInstance();
                                 XMLEvent fav_folder = null;
                                while(rdr.hasNext()) {
                                    fav_folder  = rdr.nextEvent();

                                    if(fav_folder.isEndElement())
                                        break;


                                     writer.add(fav_folder);
                                      
                              }
                              
                              for(int i = 0; i < ((JPanel)((JViewport)(((JScrollPane)files.getComponent(0)).getComponent(0))).getComponent(0)).getComponentCount(); i++) {
                                            whole_panel = (JPanel)((JViewport)(((JScrollPane)files.getComponent(0)).getComponent(0))).getComponent(0);

                                            if(((JPanel) whole_panel.getComponent(i)).getComponent(0) instanceof JLabel) {
                                                lbl = (JLabel)(((JPanel) whole_panel.getComponent(i)).getComponent(0));
                                                for(int j = 0; j < lbl.getComponentCount(); j++) {
                                                    if(lbl.getComponent(j).isVisible()) {
                                                        writer.add(event.createSpace(System.getProperty("line.separator")));
                                                       XMLEvent sampleElement = event.createCharacters("<directory name=\"" + lbl.getText() +"\"" + " path=" + "\"" + Paths.get(get_path((JPanel)path.getComponent(0)), lbl.getText()) +"\"/>");
                                              writer.add(sampleElement);
                                              button_adder(links, sampleElement, 0, (JPanel) path.getComponent(0), files, hidden, edit);
                                                        
                                                        
                                                        writer.flush();
                                                        break;
                                                        
                                                    }
                                                }
                                              }
                              }
                              
                              if(fav_folder != null)
                                  writer.add(fav_folder);
                              Files.move(tmp_file, xml.toPath(), StandardCopyOption.REPLACE_EXISTING);
                             writer.close();
                             
                            
                           
                          } catch (IOException | XMLStreamException  ex) {
                              Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                          } 
                              
                              
                          }
                      }
                      else {
                          fldr.mkdir();
                          xml = Paths.get(fldr.getAbsolutePath(), "properties.xml").toFile();
                          
                          try {
                              xml.createNewFile();
                              
                              XMLOutputFactory output = XMLOutputFactory.newInstance();
                              XMLEventWriter writer = output.createXMLEventWriter((new FileOutputStream(xml)));
                              
                              XMLEventFactory event = XMLEventFactory.newInstance();
                              writer.add(event.createStartDocument("UTF-8", "1.0"));
                              
                              writer.add(event.createStartElement(new QName("favorites"), null, null));
                              
                              for(int i = 0; i < ((JPanel)((JViewport)(((JScrollPane)files.getComponent(0)).getComponent(0))).getComponent(0)).getComponentCount(); i++) {
                                  whole_panel = (JPanel)((JViewport)(((JScrollPane)files.getComponent(0)).getComponent(0))).getComponent(0);
                                  
                                  if(((JPanel) whole_panel.getComponent(i)).getComponent(0) instanceof JLabel) {
                                      lbl = (JLabel)(((JPanel) whole_panel.getComponent(i)).getComponent(0));
                                      for(int j = 0; j < lbl.getComponentCount(); j++) {
                                          if(lbl.getComponent(j).isVisible()) {
                                              XMLEvent sampleElement = event.createCharacters("<directory name=\"" + lbl.getText() +"\"" + " path=" + "\"" + Paths.get(get_path((JPanel)path.getComponent(0)), lbl.getText()) +"\"/>");
                                              writer.add(sampleElement);
                                              button_adder(links, sampleElement, 0, (JPanel) path.getComponent(0), files, hidden, edit);
                                              writer.add(event.createEndDocument());
                                              links.repaint();
                                              writer.flush();
                                          }
                                      }
                                    }
                                  
                              }
                              
                              
                            writer.close();
                           
                          } catch (IOException | XMLStreamException  ex) {
                              Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                          } 
                      }
                      favs.repaint();
              }
          });
        
         rename.addMouseListener(new MouseAdapter() { // Dhmiourgia eventListener gia to an patithike to exit
              @Override
              public void mouseReleased(MouseEvent e) {
                  if(SwingUtilities.isLeftMouseButton(e)) {
                      if(files.getComponent(0) instanceof JScrollPane) {
                      JScrollPane pane = (JScrollPane)files.getComponent(0);
                        JPanel panel = (JPanel)((JViewport)pane.getComponent(0)).getComponent(0);
                        for(int i = 0; i < panel.getComponentCount(); i++) {
                           // for (MouseListener mouseListener : panel.getComponent(i).getComponentAt(100-75/2, 0).getMouseListeners()) {
                                if(panel.getComponent(i).getComponentAt((100-75)/2, 0) instanceof JLabel) {
                                    JLabel lbl = (JLabel)panel.getComponent(i).getComponentAt((100-75)/2, 0);
                                    
                                    if(lbl.getComponentCount() >= 1) {
                                        
                                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                JFrame msg = new JFrame("Rename file");
                                                msg.setBounds(files.getWidth()/2, files.getHeight()/2, 300, 150);
                                                JTextField text = new JTextField(lbl.getText());
                                                text.setFont(new Font("Serif", Font.PLAIN, 25));
                                                msg.add(text);
                                                msg.setVisible(true);
                                                java.io.File old_file = Paths.get(get_path((JPanel)path.getComponent(0)), lbl.getText()).toFile();
                                                
                                                text.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        java.io.File new_file = Paths.get(get_path((JPanel)path.getComponent(0)), text.getText()).toFile();
                                                        old_file.renameTo(new_file);
                                                        files.removeAll();
                                                        boolean hide;
                                                        
                                                        hide = hidden.isSelected();
                                                        java.io.File dir = new java.io.File(get_path((JPanel)path.getComponent(0)));
                                                        ((JPanel)path.getComponent(0)).removeAll();
                                                        folder(dir, files, (JPanel) path.getComponent(0), hide, edit);
                                                        
                                                        
                                                        
                                                        msg.dispose();
                                                    }
                                                });
                                                
                                            }
                                        });
                                        
                                    }
                                            
                                }
                            //}
                        }
                      }
                  }
              }
          });
          
          delete.addMouseListener(new MouseAdapter() { // Dhmiourgia eventListener gia to an patithike to exit
              @Override
              public void mouseReleased(MouseEvent e) {
                   JLabel whole_path = new JLabel();
                  whole_path.setText(get_path((JPanel)(path.getComponent(0))));
                  if(SwingUtilities.isLeftMouseButton(e)) {
                      String checked = copy(files, whole_path);
                      
                      javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                JFrame fr = new JFrame();
                                                fr.setSize(100, 100);
                                                
                                    int response = JOptionPane.showConfirmDialog(fr,"Delete file: " + checked +"?");
                                    
                                    if(response == JOptionPane.YES_OPTION) {
                                        files.removeAll();
                                        File curr_dir = new File(get_path((JPanel)(path.getComponent(0))));
                                        ((JPanel)(path.getComponent(0))).removeAll();
                                        delete(checked, files, path, hidden);
                                        folder(curr_dir, files, (JPanel)(path.getComponent(0)), hidden.isSelected(), edit);
                                        files.repaint();
                                    }
                                            }
                      });
                              
                      
                      
                  }
              }
          });
          
          properties.addMouseListener(new MouseAdapter() {
              public void mouseReleased(MouseEvent e) {
                  JLabel whole_path = new JLabel();
                  whole_path.setText(get_path((JPanel)(path.getComponent(0))));
                  JLabel lbl, name, pathname, size;
                  int max_length, max_height;
                  if(SwingUtilities.isLeftMouseButton(e)) {
                      if(files.getComponent(0) instanceof JScrollPane) {
                      JScrollPane pane = (JScrollPane)files.getComponent(0);
                        JPanel panel = (JPanel)((JViewport)pane.getComponent(0)).getComponent(0);
                        for(int i = 0; i < panel.getComponentCount(); i++) {
                            if(panel.getComponent(i).getComponentAt((100-75)/2, 0) instanceof JLabel) {
                                lbl = (JLabel)panel.getComponent(i).getComponentAt((100-75)/2, 0);
                                
                                if(lbl.getComponentCount() >= 1) {
                                    Font new_font = new Font("Serif", Font.PLAIN, 20);
                                    
                                    AffineTransform affinetransform = new AffineTransform();
                                    FontRenderContext frc = new FontRenderContext(affinetransform,true,true);   
                                    
                                    // Gia ypologismo tou megethous
                                    if(lbl.getComponent(0).isVisible()) {
                                        long size_file;
                                        
                                        JFrame fr = new JFrame("Properties");
                                        size = new JLabel();
                                        name = new JLabel();
                                        pathname = new JLabel();
                                        pathname.setText("Absolute path: " + whole_path.getText());
                                        pathname.setFont(new_font);
                                        name.setFont(new_font);
                                        size.setFont(new_font);
                                        name.setText("Name: " + lbl.getText());
                                        
                                        size_file = folder_size(Paths.get(get_path((JPanel)(path.getComponent(0))), lbl.getText()));
                                        
                                        
                                        if(size_file / 1024 != 0) {
                                            if(size_file / (1024*1024) != 0) {
                                                size.setText("Size: " + String.valueOf(size_file/(1024*1024)) + " MB");
                                            }
                                            else
                                                size.setText("Size: " + String.valueOf(size_file/1024) + " KB");
                                        }
                                        else
                                            size.setText("Size: " + String.valueOf(size_file + " B"));
                                         
                                         if(new_font.getStringBounds(name.getText(), frc).getWidth() < new_font.getStringBounds(pathname.getText(), frc).getWidth())
                                             max_length = (int)new_font.getStringBounds(pathname.getText(), frc).getWidth() + 1;
                                         
                                         else
                                             max_length = (int)new_font.getStringBounds(name.getText(), frc).getWidth() + 1;
                                         
                                         if(max_length < new_font.getStringBounds(size.getText(), frc).getWidth())
                                             max_length = (int)new_font.getStringBounds(size.getText(), frc).getWidth() + 1;
                                         
                                         if(new_font.getStringBounds(name.getText(), frc).getHeight() < new_font.getStringBounds(pathname.getText(), frc).getHeight())
                                             max_height = (int)new_font.getStringBounds(pathname.getText(), frc).getHeight()+ 1;
                                         
                                         else
                                             max_height = (int)new_font.getStringBounds(name.getText(), frc).getHeight()+ 1;
                                         
                                         if(max_length < new_font.getStringBounds(size.getText(), frc).getWidth())
                                             max_height = (int)new_font.getStringBounds(size.getText(), frc).getHeight()+ 1;
                                         
                                         
                                        fr.setBounds(1920/3 - 10, 1080/3, max_length + 30, max_height*5 + 60);
                                        JPanel prpts = frame_creator(fr, Color.LIGHT_GRAY, 10, 10, max_length + 10, max_height*5 + 10, max_length + 30, max_height*5 + 60);
                                        prpts.add(name);
                                        prpts.setLayout(new GridLayout(3, 1));
                                        prpts.add(pathname);
                                        prpts.add(size);
                                        fr.setResizable(false);
                                        fr.setVisible(true);
                                    }
                                }
                            }
                        }
                      }
                  }
              }
          });
          
          copy.addMouseListener(new MouseAdapter() { // Dhmiourgia eventListener gia to an patithike to exit
              @Override
              public void mouseReleased(MouseEvent e) {
                  JLabel whole_path = new JLabel();
                  whole_path.setText(get_path((JPanel)(path.getComponent(0))));
                  
                  if(SwingUtilities.isLeftMouseButton(e)) {
                      MouseListener e1;
                      int i;
                      String copied_file = copy(files, whole_path);
                      paste.setEnabled(true);
                      // Diagrafh twn prohgoumenwn mouse listeners
                      for(i = 1; i < paste.getMouseListeners().length ; i++) {
                          e1 = paste.getMouseListeners()[i];
                          paste.removeMouseListener(e1);
                          
                      }
                      
                      // Elegxos an exoun allaksei patera ta stoixeia... An nai ta ksanavazoume sto edit
                      JPopupMenu files_menu = files.getComponentPopupMenu();
                      JMenuItem files_paste = (JMenuItem) files_menu.getComponent(0);
                      files_paste.setEnabled(true);
                      for(i = 1; i < files_paste.getMouseListeners().length ; i++) {
                          e1 = files_paste.getMouseListeners()[i];
                          files_paste.removeMouseListener(e1);
                          files_paste.setVisible(true);
                      }
                      
                      paste.addMouseListener( new MouseAdapter() {
                          @Override
                          public void mouseReleased(MouseEvent e1) {
                              JPanel panel;
                              JLabel lbl, dst_lbl = null;
                              
                              
                              if(SwingUtilities.isLeftMouseButton(e1)) {
                                  if(!paste(files, path, hidden, false, copied_file, edit)) { // Ean epistrafei lathos tote antigrafoume to copied_file
                                      
                                      if(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0) instanceof JPanel) {
                                      for(int i = 0; i <(((JPanel)(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0))).getComponentCount()); i++) {
                                          if(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0) instanceof JPanel) {
                                      panel = ((JPanel)(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0)));
                                      lbl = (JLabel)(panel.getComponent(i).getComponentAt(0, 0));
                                      
                                      if(lbl.getComponentCount() > 1 ) {
                                          if(lbl.getComponent(1).isVisible()) {
                                              dst_lbl = lbl; // Sto dst lbl apothikevetai h timh proorismou
                                            
                                          }
                                      }
                                      }   
                                      }
                                  }
                                      
                                      
                                      
                                      if(dst_lbl != null) {
                                        Path dst = Paths.get(get_path((JPanel)(path.getComponent(0))), dst_lbl.getText(), Paths.get(copied_file).getFileName().toString());
                                        
                                        if(dst.equals(Paths.get(copied_file))) {
                                            
                                        }
                                        else
                                            copy_file(dst, Paths.get(copied_file), path, files, hidden, false, edit);
                                        
                                      }
                                      
                                  }
                                  files_paste.setEnabled(false);
                                  paste.setEnabled(false);
                              }
                          }
                      });
                      
                      
                      files_paste.addMouseListener( new MouseAdapter() {
                          @Override
                          public void mouseReleased(MouseEvent e1) {
                              if(SwingUtilities.isLeftMouseButton(e1)) {
                                  copy_file(Paths.get(get_path((JPanel)(path.getComponent(0))), Paths.get(copied_file).getFileName().toString()), Paths.get(copied_file), path,  files,  hidden,  false,  edit);
                                  files.removeAll();
                                  File curr_dir = new File(get_path((JPanel)(path.getComponent(0))));
                                  ((JPanel) path.getComponent(0)).removeAll();
                                  folder(curr_dir, files, ((JPanel) (path.getComponent(0))), hidden.isSelected(), edit);
                                  ((JPanel) (path.getComponent(0))).repaint();
                                  files.repaint();
                                  files_paste.setEnabled(false);
                                  paste.setEnabled(false);
                              }
                          }
                      });
                      
                  }
              }
          });
          
          cut.addMouseListener(new MouseAdapter() { // Dhmiourgia eventListener gia to an patithike to exit
              @Override
               public void mouseReleased(MouseEvent e) {
                   JLabel whole_path = new JLabel();
                  whole_path.setText(get_path((JPanel)(path.getComponent(0))));
                  if(SwingUtilities.isLeftMouseButton(e)) {
                      MouseListener e1;
                      int i;
                      String copied_file = copy(files, whole_path);
                      paste.setEnabled(true);
                      // Diagrafh twn prohgoumenwn mouse listeners
                      for(i = 1; i < paste.getMouseListeners().length ; i++) {
                          e1 = paste.getMouseListeners()[i];
                          paste.removeMouseListener(e1);
                          paste.setVisible(true);
                      }
                      JPopupMenu files_menu = files.getComponentPopupMenu();
                      JMenuItem files_paste = (JMenuItem) files_menu.getComponent(0);
                      files_paste.setEnabled(true);
                      
                      files_paste.setEnabled(true);
                       paste.addMouseListener( new MouseAdapter() {
                          @Override
                          public void mouseReleased(MouseEvent e1) {
                              JPanel panel;
                              JLabel lbl, dst_lbl = null;
                              
                              if(SwingUtilities.isLeftMouseButton(e1)) {
                                  if(!paste(files, path, hidden, true, copied_file, edit)) { // Ean epistrafei lathos tote antigrafoume to copied_file
                                      
                                      if(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0) instanceof JPanel) {
                                      for(int i = 0; i <(((JPanel)(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0))).getComponentCount()); i++) {
                                          if(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0) instanceof JPanel) {
                                      panel = ((JPanel)(files.getComponent(0).getComponentAt(0, 0).getComponentAt(0, 0)));
                                      lbl = (JLabel)(panel.getComponent(i).getComponentAt(0, 0));
                                      
                                      if(lbl.getComponentCount() > 1 ) {
                                          if(lbl.getComponent(1).isVisible()) {
                                              dst_lbl = lbl; // Sto dst lbl apothikevetai h timh proorismou
                                            
                                          }
                                      }
                                      }   
                                      }
                                  }
                                      
                                      if(dst_lbl != null) {
                                        Path dst = Paths.get(((JLabel)path.getComponent(0)).getText(), dst_lbl.getText(), Paths.get(copied_file).getFileName().toString());
                                        
                                        
                                        if(dst.equals(Paths.get(copied_file))) {
                                            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {

                                                JPanel panel = new JPanel(new BorderLayout());
                                                panel.setVisible(true);
                                                JOptionPane.showMessageDialog(panel, "Could not copy file on same folder file");
                                        }
                                    });  
                                        }
                                         
                                        
                                      }
                                  }
                                  files_paste.setEnabled(false);
                                  paste.setEnabled(false);
                                  files.removeAll();
                                  File curr_dir = new java.io.File(get_path((JPanel)( path.getComponent(0))));
                                                            ((JPanel) path.getComponent(0)).removeAll();
                                                            folder(curr_dir, files, (JPanel) (path.getComponent(0)), hidden.isSelected(), edit);
                                                            files.repaint();
                                                            files_paste.setEnabled(false);
                                  
                              }
                          }
                      });
                       
                       files_paste.addMouseListener( new MouseAdapter() {
                          @Override
                          public void mouseReleased(MouseEvent e1) {
                              if(SwingUtilities.isLeftMouseButton(e1)) {
                                  copy_file(Paths.get(get_path((JPanel)(path.getComponent(0))), Paths.get(copied_file).getFileName().toString()), Paths.get(copied_file), path,  files,  hidden,  true,  edit);
                                  
                                  paste.setEnabled(false);
                                  files.removeAll();
                                  File curr_dir = new File(get_path((JPanel)(path.getComponent(0))));
                                  ((JPanel) path.getComponent(0)).removeAll();
                                  folder(curr_dir, files, ((JPanel) (path.getComponent(0))), hidden.isSelected(), edit);
                                  ((JPanel) (path.getComponent(0))).repaint();
                                  files_paste.setEnabled(false);
                                  paste.setEnabled(false);
                                  files.repaint();
                              }
                          }
                      });
                      
                  }
              }
          });
    }
        
    static class searching extends Thread {
        String type, searched;
        JPanel searching_files, new_files;
        JPanel startPath;
        JMenu new_edit;
        JCheckBox new_hidden;
        
        
        @Override
        public void run() {
            Thread basic_thrd;
            basic_thrd = Thread.currentThread();
            
            for(String paths:new File(get_path((JPanel) startPath.getComponent(0))).list() ) {
                if(basic_thrd.isInterrupted())
                    return;
                Pattern pattern = Pattern.compile(searched.toLowerCase(), Pattern.CASE_INSENSITIVE);
                searching_files(Paths.get(get_path((JPanel) startPath.getComponent(0)), paths).toFile(), pattern, searching_files, startPath, new_hidden.isSelected(), new_edit, type, basic_thrd);
                
            }
        }
        
        searching(JPanel files, JTextField searchf, JPanel path, JCheckBox hidden, JMenu edit) {
            type = null;
            searched = searchf.getText();
            for(int i = 0; i < searchf.getText().length(); i++) {
                       if(searchf.getText().charAt(i) == ' ') {
                           
                           if(searchf.getText().length() - i > 7) {
                               if(searchf.getText().charAt(i + 1) == 't' && searchf.getText().charAt(i + 2) == 'y' && searchf.getText().charAt(i + 3) == 'p' && searchf.getText().charAt(i + 4) == 'e') {
                                   if(searchf.getText().charAt(i + 5) == ':') {
                                       type = searchf.getText().substring(i + 6, searchf.getText().length());
                                       searched = searchf.getText().substring(0, i);
                                       break;
                                   }
                               }
                           }
                           
                           
                       }
                   }
            
            new_edit = edit;
            new_hidden = new JCheckBox();
            new_hidden.setSelected(hidden.isSelected());
            startPath = path;
            
            if(path.getComponentCount() == 1) {
                if(path.getComponent(0) instanceof JPanel) {
                    JPanel fullPath;
                    fullPath = new JPanel();
                    JLabel pth = new JLabel();
                    pth.setText(get_path((JPanel) path.getComponent(0)));
                    fullPath.add(pth);
                }
            }
            
            searching_files = new JPanel();
            searching_files.setSize(files.getWidth() - 20, 0);
            searching_files.setLayout(new GridLayout(0, 1));
            searching_files.setBackground(null);
            new_files = files;
        }
        
        void  searching_files(File curr_file, Pattern pattern, JPanel list, JPanel path, boolean hidden, JMenu actions, String type, Thread basic_thrd) {
        
            JLabel full_name;
            ImageIcon ic;
            String suffix;
            
            if(basic_thrd.isInterrupted()) {
                return;
            }
            
            if(curr_file.isDirectory()) {
               
            Matcher matcher = pattern.matcher(curr_file.getName().toLowerCase());
            
            if(matcher.find()) {
                ic = new ImageIcon("./icons/folder.png");
                
                
                Image image = ic.getImage().getScaledInstance(40, 20, 0);
                ic = new ImageIcon(image);
                full_name = new JLabel(curr_file.getAbsolutePath(), ic, JLabel.LEFT);
                Font new_font = new Font("Serif", Font.PLAIN, 18);
                    if(type != null) {
                        if(type.equals("dir")) {
                        full_name.setFont(new_font);
                        
                        AffineTransform affinetransform = new AffineTransform();
                        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
                        
                        JPanel panel = panel_on_panel(list, null, 0, 0, list.getWidth(), (int)new_font.getStringBounds(Paths.get(curr_file.getAbsolutePath()).toString(), frc).getHeight(), 0, 0);
                        list.setSize(list.getWidth(), list.getHeight() + (int)new_font.getStringBounds(Paths.get(curr_file.getAbsolutePath()).toString(), frc).getHeight() + 5);
                        panel.add(full_name);
                        panel.setVisible(true);
                        list.add(panel);
                        list.setLayout(new GridLayout(((GridLayout)(list.getLayout())).getRows() + 1, 1));
                        list.revalidate();
                        
                        full_name.addMouseListener(new MouseAdapter() {
                           public void mouseReleased(MouseEvent e) {
                               Object src = e.getSource();
                              
                               if(SwingUtilities.isLeftMouseButton(e)) {
                                   
                                   new_files.removeAll();
                                   ((JPanel)(path.getComponent(0))).removeAll();
                                   folder(new File(((JLabel)(src)).getText()), new_files, (JPanel) (path.getComponent(0)), hidden, actions);
                                   new_files.revalidate();
                                   new_files.repaint();
                                   ((JPanel)(path.getComponent(0))).repaint();
                               }
                           }
                        });
                        }
                    }
                    else {
                        full_name.setFont(new_font);
 //                       
                        AffineTransform affinetransform = new AffineTransform();
                        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
                        JPanel panel = panel_on_panel(list, null, 0, 0, list.getWidth(), (int)new_font.getStringBounds(Paths.get(curr_file.getAbsolutePath()).toString(), frc).getHeight(), 0, 0);
                        list.setSize(list.getWidth() - 30, list.getHeight() + (int)new_font.getStringBounds(curr_file.getAbsolutePath(), frc).getHeight() + 5);
                        panel.add(full_name);
                        panel.setVisible(true);
                        list.add(panel);
                        list.setLayout(new GridLayout(((GridLayout)(list.getLayout())).getRows() + 1, 1));
                        list.revalidate();
                        
                        full_name.addMouseListener(new MouseAdapter() {
                           public void mouseReleased(MouseEvent e) {
                               Object src = e.getSource();
                              
                               if(SwingUtilities.isLeftMouseButton(e)) {
                                   
                                   new_files.removeAll();
                                   ((JPanel)(path.getComponent(0))).removeAll();
                                    path.add(new JPanel());
                                   folder(new File(((JLabel)(src)).getText()), new_files, (JPanel) (path.getComponent(0)), hidden, actions);
                                   new_files.revalidate();
                                   new_files.repaint();
                                   ((JPanel)(path.getComponent(0))).repaint();
                               }
                           } 
                        });
                    }                
                        
               }
               if(curr_file.canRead()) {
                if(curr_file.list() != null)
                    for(String pathn : curr_file.list()) {
                            if(basic_thrd.isInterrupted()) {
                                return;
                            }

                            searching_files(Paths.get(curr_file.getAbsolutePath(), pathn).toFile(), pattern, list, path, hidden, actions, type, basic_thrd);

                    }
               }
         
            
        }
        else {
                
            if(type != null)
                    if(type.equals("dir"))
                        return;
            
            Matcher matcher = pattern.matcher(curr_file.getName().toLowerCase());
            
            if(matcher.find()) {
                    if(curr_file.getPath().length() < 4) {
                        suffix = "a";
                        ic = new ImageIcon("./icons/question.png");
                    }
                    else {
                        
                             try(BufferedReader in = new BufferedReader(new FileReader("./icons/"+curr_file.getPath().substring(curr_file.getPath().length()-3, curr_file.getPath().length())+".png"))) {
                                 if(curr_file.getPath().charAt(curr_file.getPath().length()-4) == '.') {
                                     suffix = curr_file.getPath().substring(curr_file.getPath().length()-3, curr_file.getPath().length());
                                    ic = new ImageIcon("./icons/"+curr_file.getPath().substring(curr_file.getPath().length()-3)+".png");
                                 }
                                 else {
                                     ic = new ImageIcon("./icons/question.png");
                                     suffix = "a";
                                 }
                                 
                             } catch(FileNotFoundException ex) {
                                 suffix = "a";
                                 ic = new ImageIcon("./icons/question.png");
                             } catch(IOException ex2) {
                                 suffix = "a";
                                 ic = new ImageIcon("./icons/question.png");
                             }
                    }
//                
                    if(type != null) {
                        if(!type.equals(suffix))
                            return;
                    }
//                    
                Image image = ic.getImage().getScaledInstance(40, 20, 0);
                ic = new ImageIcon(image);
                full_name = new JLabel(curr_file.getAbsolutePath(), ic, JLabel.LEFT);
                Font new_font = new Font("Serif", Font.PLAIN, 18);
                       full_name.setFont(new_font);
//                       
                       AffineTransform affinetransform = new AffineTransform();
                       FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
                       JPanel panel = panel_on_panel(list, null, 0, 0, list.getWidth(), (int)new_font.getStringBounds(Paths.get(curr_file.getPath()).toString(), frc).getHeight(), 0, 0);
                       list.setSize(list.getWidth() - 30, list.getHeight() + (int)new_font.getStringBounds(curr_file.getPath(), frc).getHeight() + 5);
                       panel.add(full_name);
                       panel.setVisible(true);
                       list.add(panel);
                        list.setLayout(new GridLayout(((GridLayout)(list.getLayout())).getRows() + 1, 1));
                       list.revalidate();
//                       
                       full_name.addMouseListener(new MouseAdapter() {
                           public void mouseReleased(MouseEvent e) {
//                               
                               if(SwingUtilities.isLeftMouseButton(e)) {
                                   if(Desktop.isDesktopSupported()) {
                                                     try {
                                                        Desktop.getDesktop().open(curr_file);
                                                     } catch (IOException ex) {
                                                      
                                                         javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                                         public void run() {
                                                             JPanel panel = new JPanel(new BorderLayout());
                                                             panel.setVisible(true);
                                                             JOptionPane.showMessageDialog(panel, "Cannot run");
                                                         }
                                                     });
                                                     }
                                                 }
                               }
                           } 
                        });
            } 
        }
        }
    }
}
