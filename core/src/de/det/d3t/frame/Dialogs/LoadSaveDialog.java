package de.det.d3t.frame.Dialogs;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.controller.LevelController;
import de.det.d3t.frame.GameFrame;
import de.det.d3t.frame.MenuFrame;
import de.det.d3t.frame.SetupGameFrame;


	public class LoadSaveDialog extends Dialog{
		private Image background;
		
		private Label title;
	    
	    private String titleText;
	    
	    private boolean gameWon;

	    private float width;
	    
	    private float height;
	    
	    private Game game;

	    private Music music;
	     
	    private TextButton buttonLoad, buttonBack;

	    private Table contentTable;

	    private List<String> loadableGames;
	    
	    private ScrollPane scrollpane;

	    private TextField tfSave;

	    private List.ListStyle ls;

	    private String dialogTitle = "Wähle einen zu ladenen Speicherstand aus.";

	    private String[] loadableGamesContent;

	    private int gamesCount= 50;

	    private String dialogArt = "load";
	    
	    private String filePath = "";
	    
	    private MenuFrame mf;
	    private GameFrame gf;
	    private SetupGameFrame sgf;


	    private BitmapFont font = TextureFactory.getFont("emmett", 220, Color.valueOf("DDDCE0"));


	    public LoadSaveDialog(Game game, float width, float height, String titleText, Music music, String dialogArt, MenuFrame mf){
	    	this.game = game;
	        this.width = width;
	        this.height = height;
	        this.titleText = titleText;
	        this.music = music;
	        this.dialogArt = dialogArt;
	        this.filePath = Gdx.files.getLocalStoragePath() + "/saveGames/"; //System.getProperty("user.home") + "/saveGames/";
	        this.mf = mf;
	        
	    }
	    
	    public LoadSaveDialog(Game game, float width, float height, String titleText, Music music, String dialogArt, SetupGameFrame sgf){
	    	this.game = game;
	        this.width = width;
	        this.height = height;
	        this.titleText = titleText;
	        this.music = music;
	        this.dialogArt = dialogArt;
	        this.filePath = Gdx.files.getLocalStoragePath() + "/saveGames/";
	        this.sgf = sgf;
	        
	    }
	    
	    public LoadSaveDialog(Game game, float width, float height, String titleText, Music music, String dialogArt, GameFrame gf){
	    	this.game = game;
	        this.width = width;
	        this.height = height;
	        this.titleText = titleText;
	        this.music = music;
	        this.dialogArt = dialogArt;
	        this.filePath = Gdx.files.getLocalStoragePath() + "/saveGames/";
	        this.gf = gf;
	        
	    }
		
	    @Override
		public void showDialog(){
			Label.LabelStyle style = new Label.LabelStyle();
			style.font= TextureFactory.getFont("emmett", 400, Color.valueOf("DDDCE0"));
	        title = new Label(titleText, style);
	        
	        background = new Image(TextureFactory.getTexture("selectedLevelBackground"));
			
			if(mf!=null){
				background.setBounds(width/2  + 1500, height/2  -800, 10000, 7000);
				title.setBounds(background.getX() + background.getWidth()/2 - (3600/2) , background.getY() + background.getWidth()/2 +1000 , 4000, 700);
			}
			else{
				background.setBounds(width/2 - (8000/2) - 500, height/2 - (4000/2) -1300, 10000, 7000);
				title.setBounds(background.getX() + background.getWidth()/2 - (3600/2) , background.getY() + background.getWidth()/2 +1000 , 4000, 700);
			}

	        BitmapFont fontTitle = TextureFactory.getFont("emmett", 350, Color.valueOf("DDDCE0"));
	   
			TextButtonStyle textButtonStyleTitle = new TextButtonStyle();
			textButtonStyleTitle.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew")));
			textButtonStyleTitle.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_down")));
			textButtonStyleTitle.font = fontTitle;
			textButtonStyleTitle.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_over")));
	        
			BitmapFont fontContent= TextureFactory.getFont("emmett", 280, Color.valueOf("DDDCE0"));
			
			TextButtonStyle textButtonStyle = new TextButtonStyle();
			textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew")));
			textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_down")));
			textButtonStyle.font = fontContent;
			textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_over")));
	          
	        ls = new List.ListStyle();
	        ls.font = TextureFactory.getFont("emmett", 350, Color.valueOf("DDDCE0"));
	        ls.selection = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("listSelection")));//
	        ls.background = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("listBackground")));// 
	        ls.fontColorSelected = Color.BLUE;
	        ls.fontColorUnselected = Color.BLACK;
	        


	        boolean success = new java.io.File(this.filePath).mkdirs();
	        
	        File folder = new File(this.filePath);
	        File[] listOfFiles = folder.listFiles();
	        
	        loadableGamesContent = new String[listOfFiles.length];
	        int i = 0;
	        System.out.println("Seekind for files : in: " + folder.getAbsolutePath());
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			    	loadableGamesContent[i] = file.getName().replace(".d3t", "");
			    }
			    i++;
			}

	        ScrollPane.ScrollPaneStyle spStyle = new ScrollPane.ScrollPaneStyle();
	        spStyle.background = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("invis")));
	        spStyle.vScroll = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("listBar")));
	        spStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("listKnob")));
	       // spStyle.vScrollKnob.setRightWidth(Gdx.graphics.getWidth()/100);
	       // spStyle.vScroll.setRightWidth(Gdx.graphics.getWidth()/100);
	        //spStyle.corner = new TextureRegionDrawable(new TextureRegion(RessourceManager.ressourceManager.getTexture("sliderBar.png")));

	        Label.LabelStyle style2 = new Label.LabelStyle();
	        style2.font = font;
	        style2.fontColor = new Color(255, 0, 255, 1);


	        buttonBack = new TextButton("Abbrechen", textButtonStyle);

	        loadableGames = new List<String>(ls);
	        loadableGames.setItems(loadableGamesContent);
	        loadableGames.setBounds(background.getX() + background.getWidth()/2 -3000 , background.getY() + background.getWidth()/2 , 6000, 6000);
	        scrollpane = new ScrollPane(loadableGames,spStyle);
	        scrollpane.setScrollBarPositions(false,true);
	        scrollpane.setBounds(background.getX() + background.getWidth()/2 -3000 , background.getY() + background.getWidth()/2 - 3000 , 6500, 3500);
	        scrollpane.addListener(this);

	        if(dialogArt.equals("load")){
	            buttonLoad = new TextButton("Laden", textButtonStyle);
	            title = new Label("Bitte Speicherstand zum Laden auswählen", style);
	            title.setPosition(background.getX() + background.getWidth() / 2 * background.getScaleX() - title.getWidth() / 2 * title.getFontScaleX()- Gdx.graphics.getWidth()/30, background.getY() + background.getHeight() * 0.95f * background.getScaleY() - 1000);
	            buttonLoad.setDisabled(true);
	        }
	        else{//"save"
	            buttonLoad = new TextButton("Speichern", textButtonStyle);
	            title = new Label("Bitte Namen für den Speicherstand angeben", style);
	            title.setPosition(background.getX() + background.getWidth() / 2 * background.getScaleX() - title.getWidth() / 2 * title.getFontScaleX()- Gdx.graphics.getWidth()/30, background.getY() + background.getHeight() * 0.95f * background.getScaleY() - 1000);
	            TextField.TextFieldStyle tfS =  new TextField.TextFieldStyle();
	            tfS.font = font;
	            tfS.background = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("listSelection")));
	            tfS.cursor = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("curInput32")));
	            tfS.fontColor = Color.BLACK;
	            tfSave = new TextField("save " + new Date(),tfS);
	            tfSave.setBlinkTime(1.8f);
	            tfSave.setMaxLength(33);
	            tfSave.setDisabled(false);
	            tfSave.setBounds(background.getX() + background.getWidth()/2 + 1000 - 700 , background.getY() + background.getWidth()/2 -3000 , 3500, 400);

	        }

	        buttonLoad.setBounds(background.getX() + background.getWidth()/2 - 3000 - 700 , background.getY() + background.getWidth()/2 -4000 , 3500, 700);			
	        buttonBack.setBounds(background.getX() + background.getWidth()/2 + 1000 - 700 , background.getY() + background.getWidth()/2 -4000 , 3500, 700);      
		       
	        buttonLoad.addListener(this);
	        buttonBack.addListener(this);



	        addActor(background);
	        addActor(title);

	        addActor(scrollpane);
	        if(dialogArt.equals("save")){
	            addActor(tfSave);
	        }
	        addActor(buttonBack);
	        addActor(buttonLoad);
	        	   
		}
		
		
	    @Override
	    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	        if(event.getListenerActor() == buttonBack){
	        	return true;
	        }

	        if(event.getListenerActor() == buttonLoad){
	            return true;
	        }
	        
	        if(event.getListenerActor() == scrollpane){
	        	return true;
	        }
			return false;
	    }

	    @Override
	    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	    	 if(event.getListenerActor() == buttonLoad){
		            if(dialogArt.equals("save")){
		                
		            	File fout = new File(Gdx.files.getLocalStoragePath() + "/saveGames/"+ tfSave.getText() + ".d3t");
		            	try{
		            	FileOutputStream fos = new FileOutputStream(fout);
		             
		            	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		             
		            	for (int i = 0; i < 8; i++) {
		            		if(i == 0){
		            			bw.write("D3T");
		            		}
		            		else{
		            			boolean[] bu = Settings.getLevelUnlocked();
		            			boolean[] bc = Settings.getLevelConquered();
		            			if(bc.length<i){
		            				break;
		            			}
		            			bw.write(String.valueOf(bu[i-1]) + " " +  bc[i-1]);
		            		}
		            		bw.newLine();
		            	}
		             
		            	bw.close();
		            	}
		            	catch(Exception e){
		            		
		            	}
			        	if(sgf==null && gf==null){
			        		mf.closeDialog();
			        	}
			        	else{
			        		if(sgf==null){
			        			gf.closeDialog();
			        		}
			        		else{
			        			sgf.closeDialog();
			        		}
			        	}
			            closeDialog();
		            	
		            	
		            	
		                //this.removeFromStage();
		            }
		            else{
		            	//System.out.println(loadableGames.getSelected() + " will be loaded");
		            	File f = new File(Gdx.files.getLocalStoragePath() + "/saveGames/"+ loadableGames.getSelected() + ".d3t");
		    			FileHandle fileHandle = Gdx.files.absolute(Gdx.files.getLocalStoragePath() + "/saveGames/"+ loadableGames.getSelected() + ".d3t");
		    			if (fileHandle.exists() == false) {
		    				System.err.println("File not found: " + fileHandle.path());
		    				return;
		    			}
		    			BufferedReader buffReader = new BufferedReader(fileHandle.reader());

		    			try {
							String curLine = buffReader.readLine();
							System.out.println(curLine);
							if(!curLine.contains("D3T")){
								//return;
							}
			            	int i = 0; 
			            	while (curLine != null) {
			    				curLine = buffReader.readLine();
			    				System.out.println(curLine);
			    				if(curLine== null){
			    					System.out.println("null");
			    					break;
			    				}
			    				StringTokenizer st = new StringTokenizer(curLine);
			    				int s = 0;
			    				while(st.hasMoreTokens()){
			    					String str = st.nextToken();
			    					if(s==0){
			    						boolean[] lu = Settings.getLevelUnlocked();
			    						lu[i] = Boolean.parseBoolean(str);
			    						Settings.setLevelUnlocked(lu);
			    					}
			    					else{
			    						boolean[] lc = Settings.getLevelConquered();
			    						lc[i] = Boolean.parseBoolean(str);
			    						Settings.setLevelConquered(lc);
			    					}
			    					s++;
			    					if(s>2){
			    						break;
			    					}
			    				}
			    				
			    				if(i>6){
			    					break;
			    				}
			    				
			    				i++;
			            	}	
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.err.println("Fehler aufgetreten: " + e.getMessage());
							e.printStackTrace();
						}
		    			music.stop();
		    			game.setScreen(new SetupGameFrame(game,false));
			        	if(sgf==null && gf==null){
			        		mf.closeDialog();
			        	}
			        	else{
			        		if(sgf==null){
			        			gf.closeDialog();
			        		}
			        		else{
			        			sgf.closeDialog();
			        		}
			        	}
			            closeDialog();
		            	
		            	
		            }
		        }
		        if(event.getListenerActor() == buttonBack){
		        	if(sgf==null && gf==null){
		        		mf.closeDialog();
		        	}
		        	else{
		        		if(sgf==null){
		        			gf.closeDialog();
		        		}
		        		else{
		        			sgf.closeDialog();
		        		}
		        	}
		            closeDialog();
		        }
		        if(event.getListenerActor() == scrollpane){
		            if(dialogArt.equals("load")){
		            	buttonLoad.setDisabled(false);
		            	
		            }
		            else{
		                tfSave.clear();
		                tfSave.setText(loadableGames.getSelection().toString());
		                buttonLoad.setDisabled(false);
		            }
		        }
	    }
	}

