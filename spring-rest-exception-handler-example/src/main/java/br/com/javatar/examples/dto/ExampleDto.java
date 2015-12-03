package br.com.javatar.examples.dto;

import javax.validation.constraints.NotNull;

public class ExampleDto {

    @NotNull(message="notNull nao pode ser nulo")
    private String notNull;
    
    @NotNull(message="tamanho1 nao pode ser nulo")
    private String tamanho1;

    /**
     * @return the notNull
     */
    public String getNotNull() {
        return notNull;
    }

    /**
     * @param notNull the notNull to set
     */
    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }

    /**
     * @return the tamanho1
     */
    public String getTamanho1() {
        return tamanho1;
    }

    /**
     * @param tamanho1 the tamanho1 to set
     */
    public void setTamanho1(String tamanho1) {
        this.tamanho1 = tamanho1;
    }
    
    
}
