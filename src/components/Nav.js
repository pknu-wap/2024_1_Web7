import { Link, NavLink } from "react-router-dom";
import Container from "./Container";
import styles from "./Nav.module.css";

function Nav() {
  return (
    <div className={styles.nav}>
      <Container className={styles.container}>
        <Link to="/">
          <img alt="Dogventure Logo" />
        </Link>
        <ul className={styles.menu}>
          <li>
            <NavLink to="login">로그인</NavLink>
          </li>
          <li>
            <NavLink to="signup">회원가입</NavLink>
          </li>
        </ul>
      </Container>
    </div>
  );
}

export default Nav;
