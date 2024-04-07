import Nav from "./Nav";
import styles from "./App.module.css";
import "./App.font.css";
import { Outlet } from "react-router-dom";

function App() {
  return (
    <>
      <Nav className={styles.nav} />
      <div>
        <Outlet />
      </div>
    </>
  );
}

export default App;
