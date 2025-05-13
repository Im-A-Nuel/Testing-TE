const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3000;

app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, 'public')));

const loadUsers = () => JSON.parse(fs.readFileSync('data.json'));
const saveUsers = (data) => fs.writeFileSync('data.json', JSON.stringify(data, null, 2));

app.post('/api/login', (req, res) => {
  const { username, password } = req.body;
  const users = loadUsers();
  const user = users.find(u => u.username === username && u.password === password);
  if (user) {
    res.json({ token: `fake-token-${user.id}` });
  } else {
    res.status(401).json({ error: "Username atau password salah" });
  }
});

app.get('/api/users', (req, res) => {
  const data = loadUsers();
  res.json(data.map(({ id, name }) => ({ id, name })));
});

app.post('/api/users', (req, res) => {
  const { name, username, password } = req.body;
  const data = loadUsers();
  const newUser = {
    id: data.length ? data[data.length - 1].id + 1 : 1,
    name, username, password
  };
  data.push(newUser);
  saveUsers(data);
  res.status(201).json({ message: "User ditambahkan", user: newUser });
});

app.put('/api/users/:id', (req, res) => {
  const { id } = req.params;
  const { name } = req.body;
  let data = loadUsers();
  const userIndex = data.findIndex(u => u.id == id);
  if (userIndex === -1) return res.status(404).json({ error: "User tidak ditemukan" });
  if (name) data[userIndex].name = name;
  saveUsers(data);
  res.json({ message: "User diperbarui", user: data[userIndex] });
});

app.delete('/api/users/:id', (req, res) => {
  const { id } = req.params;
  let data = loadUsers();
  const userIndex = data.findIndex(u => u.id == id);
  if (userIndex === -1) return res.status(404).json({ error: "User tidak ditemukan" });
  const deletedUser = data.splice(userIndex, 1)[0];
  saveUsers(data);
  res.json({ message: "User dihapus", user: deletedUser });
});

app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}`);
});