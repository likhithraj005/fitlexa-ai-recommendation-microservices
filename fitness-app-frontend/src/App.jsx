import { Box, Button, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import {
  BrowserRouter as Router,
  Navigate,
  Route,
  Routes,
  useLocation,
} from "react-router";
import { setCredentials } from "./store/authSlice";
import ActivityForm from "./components/ActivityForm";
import ActivityList from "./components/ActivityList";
import ActivityDetail from "./components/ActivityDetail";
import LandingPage from "./pages/LandingPage";

const ActvitiesPage = () => {
  return (
    <Box sx={{ p: 2, border: "1px dashed grey" }}>
      <ActivityForm onActivityAdded={() => window.location.reload()} />
      <ActivityList />
    </Box> 
  );
};

function App() {
  const { token, tokenData, logIn, logOut, isAuthenticated } =
    useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  // return (
  //   <Router>

  //     {/* <div>
  //       <h1>Welcome, {tokenData?.preferred_username}!</h1>

  //       <pre>{JSON.stringify(tokenData, null, 2)}</pre>
  //       <pre>{JSON.stringify(token, null, 2)}</pre>

  //       <Button variant="contained" color="#dc004e" onClick={() => { logOut(); }}>Logout</Button>
  //     </div> */}

  //     {!token ? (
  //       <Box
  //         sx={{
  //           display: "flex",
  //           justifyContent: "space-between",
  //           alignItems: "center",
  //           px: 3,
  //           py: 2,
  //           borderBottom: "1px solid #eee",
  //           mb: 3,
  //         }}
  //       >
  //         <Typography variant="h6" sx={{ mb: 3 }}>
  //           FitNova AI Home - Your Personal Fitness Companion
  //         </Typography>

  //         <Button
  //           variant="contained"
  //           color="primary"
  //           size="large"
  //           onClick={() => {
  //             logIn();
  //           }}
  //         >
  //           LOGIN
  //         </Button>



  //       </Box>
  //     ) : (
  //       <Box sx={{ p: 2, border: "2px dashed grey" }}>

  //         <Routes>
  //           <Route path="/activities" element={<ActvitiesPage />} />
  //           <Route path="/activities/:id" element={<ActivityDetail />} />

  //           <Route
  //             path="/"
  //             element={
  //               token ? (
  //                 <Navigate to="/activities" replace />
  //               ) : (
  //                 <div>Welcome! Please Login.</div>
  //               )
  //             }
  //           />
  //         </Routes>
  //         <Button variant="contained" color="secondary" onClick={logOut} sx={{ mt: 2 }}>
  //           Logout
  //         </Button>
  //       </Box>
  //     )}
  //   </Router>
  // );


  return (
    <Router>
      <Routes>

        {/* Root route handles PKCE redirect */}
        <Route
          path="/"
          element={
            token ? (
              <Navigate to="/activities" replace />
            ) : (
              <LandingPage logIn={logIn} />
            )
          }
        />

        {/* Protected Activities */}
        <Route
          path="/activities"
          element={
            token ? <ActvitiesPage /> : <Navigate to="/" replace />
          }
        />

        <Route
          path="/activities/:id"
          element={
            token ? <ActivityDetail /> : <Navigate to="/" replace />
          }
        />

      </Routes>

      {/* Logout button */}
      {token && (
        <Box sx={{ p: 2 }}>
          <Button variant="contained" onClick={logOut}>
            Logout
          </Button>
        </Box>
      )}
    </Router>
  );

}

export default App;
