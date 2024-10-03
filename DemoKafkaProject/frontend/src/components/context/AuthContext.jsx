import { createContext, useContext, useEffect, useState } from "react";
import AuthService from "../service/AuthService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthencticated] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    if (localStorage.getItem("token") != "") {
      console.log("Token is found but not verified!");
    } else {
      setIsAuthencticated(false);
    }
  }, []);

  const login = () => {
    setIsAuthencticated(true);
    setIsAdmin(AuthService.isAdmin());
  };

  const logout = () => {
    AuthService.logout();
    setIsAuthencticated(false);
    setIsAdmin(false);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, isAdmin, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
