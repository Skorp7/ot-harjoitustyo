package dicip.domain;

import java.util.Objects;

/**
 *
 * @author sakorpi
 */
/**
 *
 * Luokka kuvaa yksittäistä käyttäjää (työntekijä tai työnjohtaja)
 */
public class User {

    private String name;
    private int status;

    public User(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object compar) {
        if (this == compar) {
            return true;
        }
        if (!(compar instanceof User)) {
            return false;
        }
        User comprUser = (User) compar;
        return this.name.equals(comprUser.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
