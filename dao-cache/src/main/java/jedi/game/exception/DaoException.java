package jedi.game.exception;

public class DaoException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = -8828605607460799115L;

    public DaoException(String string, Exception e) {
        super(string, e);
    }

    public DaoException(String string) {
        super(string);
    }

    public DaoException(Exception e) {
        super(e);
    }

    public DaoException() {
        super();
    }
}
