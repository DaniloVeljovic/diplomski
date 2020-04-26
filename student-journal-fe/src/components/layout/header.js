import React from 'react';
import { Link } from 'react-router-dom'

function Header() {
    return (
        <header style={headerStyle}>
            <h1>Studentski dnevnik</h1>
            <Link style={linkStyle} to="/">Home</Link>
            | <Link style={linkStyle} to="/about">About</Link>
            | <Link style={linkStyle} to="/obligations">Obaveze</Link>
            | <Link style={linkStyle} to="/mycourses">Moji kursevi</Link>
            | <Link style={linkStyle} to="/courses">Svi kursevi</Link>
            | <Link style={linkStyle} to="/notebooks">Radne sveske</Link>
            | <Link style={linkStyle} to="/profile">Profil</Link>
        </header>
    )
}

const headerStyle = {
    background: '#333',
    color: '#fff',
    textAlign: 'center',
    padding: '10px'
}

let linkStyle = {
    color: '#fff',
    textDecoration: 'none'
}

export default Header;