import React, { useEffect, useState } from 'react';
import NavBar from './compo/nav';
import { img1, img2, img3, img4, img7, img8, img9, img12, img14 , img15, img16, img17 } from '../resource';
import '../styles/global.css';

function Home() {
  const allProducts = [
    { prod_id: 1, prod_Img: img15, prod_Name: 'Pine-Apple', prod_Description: 'A tropical fruit rich in vitamins and enzymes. Great for digestion and boosting immunity.', prod_Price: '$10' },
    { prod_id: 2, prod_Img: img16, prod_Name: 'Apple', prod_Description: 'A nutritious fruit that is high in fiber and vitamin C. Perfect for a healthy snack.', prod_Price: '$15' },
    { prod_id: 3, prod_Img: img17, prod_Name: 'Kiwi', prod_Description: 'A small fruit packed with vitamin C and antioxidants. Helps improve skin health.', prod_Price: '$12' },
  ];

  const getImageUrl = (img) => img;

  const [animatedText, setAnimatedText] = useState('');
  const textToAnimate = "Welcome to AGRI&TRADE Company";

  useEffect(() => {
    let index = 0;
    setAnimatedText(''); // Reset before animation starts
    const interval = setInterval(() => {
      setAnimatedText(textToAnimate.slice(0, index + 1));
      index++;
      if (index === textToAnimate.length) {
        clearInterval(interval);
      }
    }, 100);
    return () => clearInterval(interval);
  }, []);

  return (
    <>
      <NavBar style={{ background: 'linear-gradient(to right, lightgreen, darkgreen)' }} />
      <div
        id="carouselExampleCaptions"
        className="carousel slide"
        style={{ height: '705px' }}
        data-bs-ride="carousel"
        data-bs-interval="7000"
      >
        <div className="carousel-indicators">
          {Array.from({ length: 3 }, (_, i) => (
            <button
              key={i}
              type="button"
              data-bs-target="#carouselExampleCaptions"
              data-bs-slide-to={i}
              className={i === 0 ? 'active' : ''}
              aria-current={i === 0 ? 'true' : 'false'}
              aria-label={`Slide ${i + 1}`}
            ></button>
          ))}
        </div>
        <div className="carousel-inner" style={{ height: '100%' }}>
          {Array.from({ length: 3 }, (_, i) => (
            <div
              key={i}
              className={`carousel-item ${i === 0 ? 'active' : ''}`}
              style={{ height: '100%' }}
            >
              <img
                src={i === 0 ? img9 : i === 1 ? img8 : img7}
                className="d-block w-100"
                alt={`Slide ${i + 1}`}
                style={{ height: '100%', objectFit: 'cover' }}
              />
            </div>
          ))}
        </div>
        <button
          className="carousel-control-prev"
          type="button"
          data-bs-target="#carouselExampleCaptions"
          data-bs-slide="prev"
        >
          <span className="carousel-control-prev-icon"></span>
        </button>
        <button
          className="carousel-control-next"
          type="button"
          data-bs-target="#carouselExampleCaptions"
          data-bs-slide="next"
        >
          <span className="carousel-control-next-icon"></span>
        </button>
      </div>

      <div className="container mt-4">
        <div className="row align-items-center mb-5">
          <div className="col-md-6 order-md-2">
            <img src={img12} alt="Fresh Organic Produce" className="img-fluid" style={{ width: '700px' }} />
          </div>
          <div className="col-md-6 order-md-1" style={{ padding: '20px' }}>
            <h2>
              <b>{animatedText}</b>
            </h2>
            <br />
            <p>Delivering Freshness & Quality for Over 5 Years</p>
            <p>
              At AGRI&TRADE, we are committed to providing nutritious and high-quality food that promotes a healthy lifestyle. 
              Our journey began with a vision to bring natural, farm-fresh produce to your table.
            </p>
            <h4>Pure & Natural Food</h4>
            <p>
              Sourced directly from trusted farms, our products retain their natural goodness, free from harmful additives.
            </p>
            <h4>Premium Quality Guaranteed</h4>
            <p>
              We uphold the highest standards in food safety and quality, ensuring every bite is as nutritious as it is delicious.
            </p>
          </div>
        </div>
      </div>

      <div
        className="mt-5 p-3"
        style={{
          backgroundColor: 'lightgreen',
          width: '100vw',
          marginLeft: '-50vw',
          left: '50%',
          position: 'relative',
        }}
      >
        <h2 className="text-center">Our Service</h2>
        <hr style={{ color: 'green', height: '10px' }} />
        <br />
        <div className="row">
          <div className="col-md-6">
            <img src={img14} alt="Service GIF" className="img-fluid" style={{ width: '600px', marginLeft: '0px' }} />
          </div>
          <div className="col-md-4">
            <p style={{ fontSize: '22px', fontWeight: 'bold', color: '#2c3e50' }}>
              At AgriTrade, we offer a wide range of services to ensure the best quality of agricultural products. 
              Our services include farm management, crop consulting, and supply chain optimization.
            </p>
            <p style={{ fontSize: '22px', fontWeight: 'bold', color: '#2c3e50' }}>
              We work closely with farmers to implement sustainable farming practices. 
              Our goal is to enhance productivity while maintaining the ecological balance.
            </p>
          </div>
        </div>
      </div>
        <br></br>
      <h2 className="text-center mb-4" style={{color:'green'}}>Your Role</h2>
      <div className="row">
        <div className="col-md-6">
          <div className="card role-card">
            <img src={img1} className="card-img-top" alt="Farmer" />
            <div className="card-body">
              <h5 className="card-title">Farmer</h5>
            </div>
          </div>
        </div>
        <div className="col-md-6">
          <div className="card role-card">
            <img src={img4} className="card-img-top" alt="Consumer" />
            <div className="card-body">
              <h5 className="card-title">Consumer</h5>
            </div>
          </div>
        </div>
      </div>

      <div
        className="mt-5 p-3"
        style={{
          backgroundColor: 'lightgreen',
          width: '100vw',
          marginLeft: '-50vw',
          left: '50%',
          position: 'relative',
        }}
      >
        <h2 className="text-center">All Products</h2>
        <br />
        <div className="row">
          {allProducts.map((prod) => (
            <div key={prod.prod_id} className="col-md-4 mb-3">
              <div className="card product-card">
                <img
                  src={getImageUrl(prod.prod_Img)}
                  alt={prod.prod_Name}
                  className="card-img-top"
                  style={{ height: '350px', objectFit: 'cover' }}
                />
                <div className="card-body">
                  <h5 className="card-title">{prod.prod_Name}</h5>
                  <p className="card-text">{prod.prod_Description}</p>
                  <p className="card-text">
                    <strong>Price:</strong> {prod.prod_Price}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      <footer
        className="text-center footer mt-8"
        style={{ backgroundColor: 'lightgreen', padding: '20px', width: '100vw', marginLeft: '-50vw', left: '50%', position: 'relative' }}
      >
        <hr style={{ color: 'green', height: '10px' }} />
        <h4>About AgriTrade</h4>
        <p>
          AgriTrade is a leading company in providing high-quality agricultural products and services. With over 5 years of
          experience, we are committed to delivering the best to our customers.
        </p>
        <p>
          <b>Contact Us:</b>
        </p>
        <p>Address: 123 AgriTrade Street, Agriculture City, AG 45678</p>
        <p>Phone: +1 (123) 456-7890</p>
        <p>Email: info@agritrade.com</p>
        <div className="social-icons">
          <a href="#">
            <i className="fab fa-facebook-f"></i>
          </a>
          <a href="#">
            <i className="fab fa-twitter"></i>
          </a>
          <a href="#">
            <i className="fab fa-instagram"></i>
          </a>
          <a href="#">
            <i className="fab fa-linkedin-in"></i>
          </a>
        </div>
        <p>
          <b>&copy; 2023 AGRI&TRADE Company. All rights reserved.</b>
        </p>
      </footer>
    </>
  );
}

export default Home;