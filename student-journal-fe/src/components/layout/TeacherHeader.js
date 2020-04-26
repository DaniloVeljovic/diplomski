import React from 'react';
import { Link } from 'react-router-dom'

function TeacherHeader() {
    return (
        <header style={headerStyle}>
            <h1>Studentski dnevnik</h1>
            <Link style={linkStyle} to="/">Home</Link>
            | <Link style={linkStyle} to="/teacherObligations">Moje obaveze</Link>
            | <Link style={linkStyle} to="/mycourses">Moji kursevi</Link>
            | <Link style={linkStyle} to="/createCourse">Kreiraj kurs</Link>
            | <Link style={linkStyle} to="/createObligation">Kreiraj obavezu za kurs</Link>
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

export default TeacherHeader;