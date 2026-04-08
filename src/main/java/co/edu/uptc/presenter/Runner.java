package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.IPresenter;
import co.edu.uptc.interfaces.IView;
import co.edu.uptc.view.MainFrame;

public class Runner {

    IPresenter presenter;
    IView view;


    private void makeMVP(){
        view = new MainFrame();
        presenter = new Presenter();

        view.setPresenter(presenter);
    }

    public void start(){
        makeMVP();
        view.start();
    }
}
