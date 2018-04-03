package view;

import java.util.logging.Handler;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/** 
 * @name: TextFieldEventHandler.java
 * @author:  sleepyocean
 * @date: created in 2018年4月3日 上午9:38:01 
 * @version: 1.0 
 * @description: TODO (write your description) 
 */

public class TextFieldEventHandler implements EventHandler<KeyEvent>{
	private IEventHandler<KeyEvent> handler;
	private TextField tf;

	public TextField getTf() {
		return tf;
	}

	public void setTf(TextField tf) {
		this.tf = tf;
	}

	public void setHandler(IEventHandler<KeyEvent> handler) {
		this.handler = handler;
	}

	@Override
	public void handle(KeyEvent arg0) {
		handler.handle(arg0,tf);
	}

	@SuppressWarnings("hiding")
	public interface IEventHandler<KeyEvent>{
		void handle(KeyEvent event,TextField tf);
	}

}


