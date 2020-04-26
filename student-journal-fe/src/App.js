import React, { Component } from 'react';
import './App.css';
import Todos from "./components/Todos"
import Header from './components/layout/header'
import TeacherHeader from './components/layout/TeacherHeader'
import CourseInfo from './components/CourseInfo'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import About from './components/pages/about'
import axios from 'axios';
import Container from '@material-ui/core/Container'
import 'bootstrap/dist/css/bootstrap.min.css';
import Obligations from './components/Obligations'
import Grid from '@material-ui/core/Grid'
import CssBaseline from '@material-ui/core/CssBaseline';
import Courses from './components/Courses'
import Paper from '@material-ui/core/Paper'
import Profile from './components/Profile'
import TeacherProfile from './components/Teacher/TeacherProfile'
import AddCourse from './components/Teacher/AddCourse'
import AddObligation from './components/Teacher/AddObligation'
import Login from './components/Login'
import RegisterStudent from './components/RegisterStudent'
import RegisterTeacher from './components/RegisterTeacher'

class App extends Component {
  state = {
    user: {},
    userID: '',
    role: '',
    loggedIn: false,
    accessToken: '',
    eventSignal: false,
    password: ''
  }


  //======================================BEGIN STUDENT=============================================
  register = (user) => {
    console.log(user)
    /*axios.post('http://localhost:8100/users-microservice/users/students',
      {
        user
      })
      .then(res => {
        console.log("primljeno je " + user);
        this.setState({
          user: user
        })
      })*/
  }


  //======================================END STUDENT=============================================

  //======================================BEGIN TEACHER============================================





  //======================================END STUDENT=============================================

  login = async (s) => {
    try {
      console.log('login')
      console.log(s)
      let res = await axios.post('http://localhost:8100/users-microservice/login', s);
      console.log(res.headers)
      this.setState({
        loggedIn: true,
        userID: res.headers.userid,
        accessToken: res.headers.token,
        role: res.headers.role
      }, () => {
        console.log(this.state)
      })
    }
    catch (err) {
      this.state.loggedIn = false;
    }
  }

  registerStudent = async (user) => {
    this.setState({ password: user.password })
    try {
      console.log("usao u if")
      let res = await axios.post('http://localhost:8100/users-microservice/users/students', user)
      console.log('res')
      console.log(res)
      const userLog = {
        'email': res.data.email,
        'password': this.state.password
      }
      console.log(userLog)
      this.login(userLog)
    }
    catch (err) {
      console.log(err)
    }

  }

  registerTeacher = async (user) => {
    this.setState({ password: user.password })
    console.log(user)
    try {
      let res = await axios.post('http://localhost:8100/users-microservice/users/teachers', user)

      const userLog = {
        'email': res.data.email,
        'password': this.state.password
      }
      this.login(userLog)

      console.log(res)
    }
    catch (err) {
      console.log(err)
    }


  }

  componentDidUpdate() {

    if (this.state.loggedIn === true && this.state.eventSignal === false) {


      //STUDENT
      if (this.state.role === 'student') {
        this.setState({ eventSignal: true })

      }
      else {

        //TEACHER



        this.setState({ eventSignal: true })
      }
    }
  }

  render() {

    let render = (<Container maxWidth="sm">

      <Router>
        <Route path='/registerStudent'>
          <RegisterStudent register={this.registerStudent} />
        </Route>
        <Route exact path='/login'>
          <Login change={this.login} />
        </Route>
        <Route path='/registerTeacher'>
          <RegisterTeacher register={this.registerTeacher} />
        </Route>
      </Router>

    </Container>);


    if (this.state.loggedIn === true) {
      render = (<Router>
        <Container maxWidth="md">
          <CssBaseline />
          <Header />
          <Route exact path="/" render={props => (
            <React.Fragment>
              <Paper> <img style={{ width: '912px', height: '500px' }} src='https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80' />  </Paper>
            </React.Fragment>
          )} />
          <Route path="/about" component={About} />
          <Route path="/obligations" render={props => (
            <React.Fragment>
              <Grid >
                <Obligations userID={this.state.userID} />
              </Grid>
            </React.Fragment>
          )} />
          <Route path="/mycourses" render={props => (
            <React.Fragment>
              <Grid >
                <Courses all={false} role={this.state.role} userID={this.state.userID} />
              </Grid>
            </React.Fragment>)} />
          <Route path="/courses" render={props => (
            <React.Fragment>
              <Grid >
                <Courses all={true} role={this.state.role} userID={this.state.userID} />
              </Grid>
            </React.Fragment>)} />
          <Route path="/notebooks" render={props => (
            <React.Fragment>
              <Grid >
                <Todos userID={this.state.userID} />
              </Grid>
            </React.Fragment>)} />
          <Route path="/profile" render={props => (
            <React.Fragment>
              <Grid>
                <Profile userID={this.state.userID} />
              </Grid>
            </React.Fragment>)} />
          <Route path="/courseInfo" render={props => (
            <CourseInfo />)} />
        </Container>
      </Router>)



      if (this.state.role === 'teacher')
        render = (
          <Router>
            <Container maxWidth="md">
              <CssBaseline />
              <TeacherHeader />
              <Route exact path="/" render={props => (
                <React.Fragment>
                  <Paper> <img style={{ width: '912px', height: '500px' }} src='https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80' />  </Paper>
                </React.Fragment>
              )} />
              <Route path="/teacherObligations" render={props => (
                <React.Fragment>
                  <Grid >
                    <Obligations role={this.state.role} userID={this.state.userID} />
                  </Grid>
                </React.Fragment>
              )} />
              <Route path="/mycourses" render={props => (
                <React.Fragment>
                  <Grid >
                    <Courses userID={this.state.userID} role={this.state.role} />
                  </Grid>
                </React.Fragment>)} />
              <Route path="/createCourse" render={props => (
                <React.Fragment>
                  <Grid >
                    <AddCourse userID={this.state.userID} />
                  </Grid>
                </React.Fragment>)} />
              <Route path="/createObligation" render={props => (
                <React.Fragment>
                  <Grid >
                    <AddObligation userID={this.state.userID} role={this.state.role} />
                  </Grid>
                </React.Fragment>)} />
              <Route path="/profile" render={props => (
                <React.Fragment>
                  <Grid>
                    <TeacherProfile userID={this.state.userID} />
                  </Grid>
                </React.Fragment>)} />
            </Container>
          </Router>
        )
    }


    return render
  }


}

export default App;

//TODO
/*
  markComplete = (id) => {
    this.setState({
      todos: this.state.todos.map(todo => {
        if (todo.id === id) {
          todo.completed = !todo.completed;
        }
        return todo;
      })
    })
  }

  delTodo = (id) => {
    axios.delete(`http://jsonplaceholder.typicode.com/todos/${id}`)
      .then(res => {
        this.setState({
          todos: [...this.state.todos.filter(todo => (todo.id !== id))]
        })
      })


  }

  addTodo = (title) => {
    axios.post('http://jsonplaceholder.typicode.com/todos',
      {
        title: title,
        completed: false
      })
      .then(res => {
        this.setState({
          todos: [...this.state.todos, res.data]
        })
      })

  } */