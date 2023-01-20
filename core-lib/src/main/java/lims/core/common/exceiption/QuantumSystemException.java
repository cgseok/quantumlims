package lims.core.common.exceiption;

public class QuantumSystemException extends RuntimeException {

    private static final long serialVersionUID = 7888445410813315385L;

    private String errorCode;
    private String errorMessage;
    private Object errorData;

    public QuantumSystemException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public QuantumSystemException(String errorCode, String errorMessage, Object errorData) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorData = errorData;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Object getErrorData() {
        return errorData;
    }

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
