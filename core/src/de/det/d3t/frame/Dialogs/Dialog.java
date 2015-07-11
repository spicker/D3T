package de.det.d3t.frame.Dialogs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.LinkedList;
import java.util.List;

public abstract class Dialog extends InputListener
{

    private Group group = new Group();
    
    private List<Object> observers;
    private boolean hasChanged;

    public Dialog()
    {
        group.addListener(this);
        observers = new LinkedList<>();
    }

    public void removeFromStage()
    {
        if(group.hasParent())
            group.getParent().removeActor(group);
    }

    public void draw(SpriteBatch batch, float parentAlpha)
    {
        getGroup().draw(batch, parentAlpha);
    }
    
    public void closeDialog()
    {
    	if(getGroup().hasParent())
    	{
    		getGroup().getParent().removeActor(getGroup());
    	}
    }

    public Group getGroup()
    {
        return group;
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    public void addActor(Actor actor)
    {
        getGroup().addActor(actor);
    }

    public void removeActor(Actor actor)
    {
        getGroup().removeActor(actor);
    }
    

    public abstract boolean touchDown (InputEvent event, float x, float y, int pointer, int button);
    public abstract void touchUp (InputEvent event, float x, float y, int pointer, int button);
    public abstract void showDialog();
}
