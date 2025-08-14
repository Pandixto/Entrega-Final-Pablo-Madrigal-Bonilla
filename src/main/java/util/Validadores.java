package util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class Validadores {
    private static final Pattern PWD =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,12}$");

    public static boolean esMayorDeEdad(LocalDate fechaNac) {
        return Period.between(fechaNac, LocalDate.now()).getYears() >= 18;
    }

    public static boolean contrasenaValida(String pwd) {
        return pwd != null && PWD.matcher(pwd).matches();
    }
}
