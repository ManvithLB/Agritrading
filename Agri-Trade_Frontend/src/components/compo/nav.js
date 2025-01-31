import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

const NavBar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [role, setRole] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const savedAuth = JSON.parse(localStorage.getItem('authData'));
    if (savedAuth && savedAuth.token) {
      setIsLoggedIn(true);
      setRole(savedAuth.role);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('authData');
    setIsLoggedIn(false);
    setRole(null);
    navigate('/');
  };

  return (
    <nav
      className={`navbar navbar-expand-lg ${location.pathname === '/' ? 'fixed-top' : ''}`}
      style={{
        background: 'linear-gradient(to right, #32a852, #59e659)',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
        padding: '10px 20px',
      }}
    >
      <a
        className="navbar-brand"
        href="#"
        style={{
          color: 'white',
          fontWeight: 'bold',
          fontSize: '1.5rem',
          textShadow: '2px 2px 4px rgba(0, 0, 0, 0.3)',
        }}
      >
        AGRI&TRADE
      </a>
      <button
        className="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon"></span>
      </button>
      <div className="collapse navbar-collapse" id="navbarNav">
        <ul className="navbar-nav me-auto">
          <li className="nav-item">
            <a
              className="nav-link"
              href="/"
              style={{
                color: 'white',
                fontWeight: '500',
                margin: '0 10px',
                transition: 'color 0.3s',
              }}
              onMouseOver={(e) => (e.target.style.color = '#c2f0c2')}
              onMouseOut={(e) => (e.target.style.color = 'white')}
            >
              Home
            </a>
          </li>
          
          {role === 'FARMER' && (
            <>
              <li className="nav-item">
                <a
                  className="nav-link"
                  href="/products"
                  style={{ color: 'white', margin: '0 10px' }}
                >
                  Product
                </a>
              </li>
              <li className="nav-item">
                <a
                  className="nav-link"
                  href="/orders"
                  style={{ color: 'white', margin: '0 10px' }}
                >
                  Orders
                </a>
              </li>
              <li className="nav-item">
                <a
                  className="nav-link"
                  href="/insightproduct"
                  style={{ color: 'white', margin: '0 10px' }}
                >
                  Get Insights
                </a>
              </li>
            </>
          )}
          {role === 'CUSTOMER' && (
            <>
              <li className="nav-item">
                <a
                  className="nav-link"
                  href="/consumer/home"
                  style={{ color: 'white', margin: '0 10px' }}
                >
                  Product
                </a>
              </li>
              <li className="nav-item">
                <a
                  className="nav-link"
                  href="/consumer/order"
                  style={{ color: 'white', margin: '0 10px' }}
                >
                  Orders
                </a>
              </li>
              <li className="nav-item">
            <a
              className="nav-link"
              href="/customer/feed"
              style={{
                color: 'white',
                fontWeight: '500',
                margin: '0 10px',
                transition: 'color 0.3s',
              }}
              onMouseOver={(e) => (e.target.style.color = '#c2f0c2')}
              onMouseOut={(e) => (e.target.style.color = 'white')}
            >
              Feedback
            </a>
          </li>
            </>
          )}
        </ul>
        <ul className="navbar-nav ms-auto">
          <li className="nav-item">
            <a
              className="nav-link"
              href={isLoggedIn ? '/profile' : '/register'}
              style={{
                color: 'white',
                margin: '0 10px',
                transition: 'color 0.3s',
              }}
              onMouseOver={(e) => (e.target.style.color = '#c2f0c2')}
              onMouseOut={(e) => (e.target.style.color = 'white')}
            >
              {isLoggedIn ? 'Profile' : 'SignUp'}
            </a>
          </li>
          <li className="nav-item">
            <a
              className="nav-link"
              href={isLoggedIn ? '#' : '/login'}
              onClick={isLoggedIn ? handleLogout : null}
              style={{
                color: 'white',
                margin: '0 10px',
                transition: 'color 0.3s',
              }}
              onMouseOver={(e) => (e.target.style.color = '#c2f0c2')}
              onMouseOut={(e) => (e.target.style.color = 'white')}
            >
              {isLoggedIn ? 'Logout' : 'Login'}
            </a>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default NavBar;
