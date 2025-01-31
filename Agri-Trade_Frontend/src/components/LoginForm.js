import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ApiService from "../ApiService";
import costsimage from "../resource/costs.png";
import { Form, Button, Alert, Card, Container, Row, Col } from "react-bootstrap";
import { img1, img2, img3, img4, img7, img8, img9, img10,img11 } from "../resource";
import "../styles/logincss.css"; // CSS for animations
import NavBar from "./compo/nav";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loginStatus, setLoginStatus] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [welcomeText, setWelcomeText] = useState("");
  const [subText, setSubText] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const welcomeMessage = "Welcome to AgriTrading";
    const subMessage = "Your gateway to smarter farming solutions";
    let welcomeIndex = 0;
    let subIndex = 0;

    const typeWelcome = () => {
      if (welcomeIndex < welcomeMessage.length) {
        setWelcomeText((prev) => welcomeMessage.slice(0, welcomeIndex + 1));
        welcomeIndex++;
        setTimeout(typeWelcome, 40);
      } else {
        typeSub();
      }
    };

    const typeSub = () => {
      if (subIndex < subMessage.length) {
        setSubText((prev) => subMessage.slice(0, subIndex + 1));
        subIndex++;
        setTimeout(typeSub, 40);
      }
    };

    typeWelcome();
  }, []);

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage("");
    setLoginStatus("");
    setIsLoading(true);

    try {
      const authData = await ApiService.loginUser(username, password);
      if (authData && authData.token) {
        setLoginStatus("Welcome back! Redirecting...");
        localStorage.setItem("authData", JSON.stringify(authData));
        localStorage.setItem("authToken", authData.token);
        setTimeout(() => navigate("/"), 1000);
      }
    } catch (error) {
      setErrorMessage("Login failed. Please check your credentials.");
      console.error("Login error:", error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
    <NavBar></NavBar>
      <div
        className="login-page"
        style={{
          backgroundImage: `url(${img10})`,
          backgroundSize: "cover",
          backgroundRepeat: "no-repeat",
          backgroundPosition: "center",
          opacity: 0.9,
        }}
      >
        <div className="form-container">
          <Container>
            <Row className="justify-content-center">
              <Col md={6} lg={5}>
                <Card
                  className="shadow-lg"
                  style={{ backgroundColor: "rgba(255, 255, 255, 0.8)" }}
                >
                  <Card.Header className="bg-success text-white text-center">
                    <img
                      src={img11}
                      alt="Farming GIF"
                      className="mb-3"
                      style={{ width: "180px" }}
                    />
                    <h3 style={{ fontSize: "1.5rem" }}>{welcomeText}</h3>
                    <p style={{ fontSize: "1rem" }}>{subText}</p>
                  </Card.Header>
                  <Card.Body>
                    <Form onSubmit={handleLogin} noValidate>
                      <Form.Group className="mb-3" controlId="formUsername">
                        <Form.Label>Username</Form.Label>
                        <Form.Control
                          type="text"
                          placeholder="Enter username"
                          value={username}
                          onChange={(e) => setUsername(e.target.value)}
                          required
                        />
                      </Form.Group>
                      <Form.Group className="mb-3" controlId="formPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                          type="password"
                          placeholder="Enter password"
                          value={password}
                          onChange={(e) => setPassword(e.target.value)}
                          required
                        />
                      </Form.Group>
                      <Button
                        type="submit"
                        className="w-100"
                        variant="success"
                        disabled={isLoading}
                      >
                        {isLoading ? "Logging in..." : "Login"}
                      </Button>
                    </Form>
                    {loginStatus && (
                      <Alert variant="success" className="mt-3">
                        {loginStatus}
                      </Alert>
                    )}
                    {errorMessage && (
                      <Alert variant="danger" className="mt-3">
                        {errorMessage}
                      </Alert>
                    )}
                    <div className="text-center mt-3">
                      <p className="small">
                        Don't have an account? <a href="/register">Sign up here</a>
                      </p>
                      <a href="/forgot-password" className="small text-muted">
                        Forgot password?
                      </a>
                    </div>
                  </Card.Body>
                </Card>
              </Col>
            </Row>
          </Container>
        </div>
      </div>
    </>
  );
};

export default LoginForm;
//hello