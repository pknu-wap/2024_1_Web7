import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null);

  const login = (token) => {
    setCurrentUser(token);
    localStorage.setItem("Authorization", token);
  };

  const logout = () => {
    setCurrentUser(null);
    localStorage.removeItem("Authorization");
  };

  return (
    <AuthContext.Provider value={{ currentUser, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
