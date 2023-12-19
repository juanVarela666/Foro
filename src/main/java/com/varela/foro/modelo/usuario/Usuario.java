package com.varela.foro.modelo.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.varela.foro.modelo.topico.Topico;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
/**
 * Representa a un usuario en el sistema del foro.
 * Esta clase implementa la interfaz UserDetails para la integración de Spring Security.
 */
@Table(name = "usuario")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;
    private String nombre;
    private String email;
    private String contrasena;

    /**
     * La lista de temas creados por el usuario.
     */
    @OneToMany(mappedBy = "autor")
    @JsonIgnoreProperties("autor")
    private List<Topico> topicos;

    /**
     * Crea un nuevo usuario a partir de los datos de usuario proporcionados.
     *
     * @param datosUsuario Datos del usuario.
     */
    public Usuario(DatosUsuario datosUsuario) {
        this.nombre = datosUsuario.nombre();
        this.email = datosUsuario.email();
        this.contrasena = datosUsuario.contrasena();
    }

    /**
     * Actualiza los datos del usuario con los datos del usuario proporcionados.
     *
     * @param datosNuevoUsuario Datos del usuario actualizados.
     * @return Instancia de usuario actualizada.
     */
    public Usuario actualizarDatos(DatosNuevoUsuario datosNuevoUsuario) {
        this.nombre = datosNuevoUsuario.nombre();
        this.email = datosNuevoUsuario.email();
        this.contrasena = datosNuevoUsuario.contrasena();
        return this;
    }

    /**
     * Crea un nuevo usuario con los datos de usuario proporcionados y codifica la contraseña utilizando el codificador de contraseña especificado.
     *
     * @param datosUsuario      Datos del usuario.
     * @param passwordEncoder   El codificador de contraseñas para codificar la contraseña.
     */
    public Usuario(DatosUsuario datosUsuario, PasswordEncoder passwordEncoder) {
        this.nombre = datosUsuario.nombre();
        this.email = datosUsuario.email();
        setContrasena(datosUsuario.contrasena(), passwordEncoder);
    }

    /**
     * Establece la contraseña codificada usando BCrypt antes de almacenarla.
     *
     * @param contrasena        La contraseña sin formato que se codificará.
     * @param passwordEncoder   El codificador de contraseñas para codificar la contraseña.
     */
    public void setContrasena(String contrasena, PasswordEncoder passwordEncoder) {
        this.contrasena = passwordEncoder.encode(contrasena);
    }

    /**
     * Obtiene las autoridades otorgadas al usuario. En esta implementación, al usuario se le otorga el rol "ROLE_USER".
     *
     * @return Una colección de GrantedAuthority que representa las autoridades del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Obtiene la contraseña codificada para el usuario.
     *
     * Obtiene el nombre de usuario (correo electrónico) del usuario.
     */
    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Comprueba si la cuenta del usuario no ha caducado.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Comprueba si la cuenta del usuario no está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Comprueba si las credenciales del usuario (contraseña) no están caducadas.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Comprueba si el usuario está habilitado.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

