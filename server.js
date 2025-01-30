const express = require('express');
const multer = require('multer');
const path = require('path');
const app = express();
const port = 5456;

// Configure multer for file uploads
const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, path.join(__dirname, 'images'));
  },
  filename: (req, file, cb) => {
    cb(null, file.originalname);
  },
});

const upload = multer({ storage });

app.post('/upload-image', upload.single('image'), (req, res) => {
  res.status(200).json({ message: 'Image uploaded successfully' });
});

// ...existing code...

app.listen(port, () => {
  console.log(`Server running on http://localhost:${port}`);
});
