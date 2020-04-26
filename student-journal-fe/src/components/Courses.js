import React, { Component } from 'react';
import Card from '@material-ui/core/Card';
import PropTypes from 'prop-types';
import Grid from '@material-ui/core/Grid'
import CssBaseline from '@material-ui/core/CssBaseline';
import CourseItem from './CourseItem'
import Axios from 'axios';
import Button from '@material-ui/core/Button';
import CourseTeacherInfo from './Teacher/TeacherCourses'
import axios from 'axios'
import { DialogTitle, Dialog, DialogActions, DialogContent, DialogContentText } from '@material-ui/core'
import Slide from '@material-ui/core/Slide';
import { createMuiTheme, ThemeProvider } from '@material-ui/core/styles';

const theme = createMuiTheme({
    overrides: {
        // Style sheet name ⚛️
        MuiButton: {
            // Name of the rule
            text: {
                // Some CSS
                background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
                borderRadius: 3,
                border: 0,
                color: 'white',
                height: 48,
                padding: '0 30px',
                margin: '0 0 0 380px',
                boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
            },
        },
    },
});


class Courses extends Component {

    state = {
        courses: [],
        activeCourse: {},
        showCourse: false,
        showEnrolForm: false,
        obligations: {},
        open: false
    }

    eventHandler = async (ev) => {
        if (this.props.role === 'teacher') {
            const found = this.state.courses.find(course => course.courseID === ev)
            console.log(found)
            let responseObligation = await Axios.get('http://localhost:8100/course-microservice/courses/' + found.courseID + '/obligations')
            console.log(responseObligation.data)
            this.setState({ obligations: responseObligation.data, activeCourse: found, showCourse: true, showEnrolForm: false }, async () => {
                try {
                    let res = await Axios.get('http://localhost:8100/course-microservice/courses/' + this.state.activeCourse.courseID + '/role/teacher/userID/' + this.props.userID)
                    console.log(res)
                }
                catch (err) {
                    console.log(err)
                    this.setState({ showEnrolForm: true, showCourse: false })
                }
            })
        }
        else {
            const found = this.state.courses.find(course => course.courseID === ev)
            console.log(found)
            let responseObligation = await Axios.get('http://localhost:8100/course-microservice/courses/' + found.courseID + '/obligations')
            console.log(responseObligation.data)
            this.setState({ obligations: responseObligation.data, activeCourse: found, showCourse: true, showEnrolForm: false }, async () => {
                try {
                    let res = await Axios.get('http://localhost:8100/course-microservice/courses/' + this.state.activeCourse.courseID + '/role/student/userID/' + this.props.userID)
                    console.log(res)
                }
                catch (err) {
                    console.log(err)
                    this.setState({ showEnrolForm: true, showCourse: false })
                }
            })
        }
    }

    enrol = async () => {
        let res = await axios.post('http://localhost:8100/course-microservice/courses/' + this.state.activeCourse.courseID + '/enrol/' + this.props.userID)
        this.handleClickOpen();
    }

    getAllCoursesForTeacher = async () => {
        try {
            let response = await axios.get('http://localhost:8100/course-microservice/teachers/' + this.props.userID);
            this.setState({
                courses: response.data
            }, () => {
                console.log(response)
            })

        }
        catch (err) {
            console.log(err)
        }
    }

    Transition = React.forwardRef(function Transition(props, ref) {
        return <Slide direction="up" ref={ref} {...props} />;
    });


    handleClickOpen = () => {
        this.setState({ open: true })

    };

    handleClose = () => {
        this.setState({ open: false })
    };

    getAllCoursesForStudent = async () => {
        try {
            let res = await axios.get('http://localhost:8100/course-microservice/students/' + this.props.userID + '/courses')
            console.log("Svi kursevi za studenta " + res.data)
            this.setState({ courses: res.data })
        }
        catch (err) {
            console.log(err)
        }
    }

    getAllCourses = async () => {
        let res = await axios.get('http://localhost:8100/course-microservice/courses')
        this.setState({ courses: res.data })

    }

    componentWillMount() {
        console.log("pre svega iz kurseva")

        if (this.props.role === 'teacher') {
            this.getAllCoursesForTeacher()
        }
        else if (this.props.role === 'student' && this.props.all === false)
            this.getAllCoursesForStudent();
        else
            this.getAllCourses();


    }

    render() {

        let show = null;

        let showEnrol = null


        if (this.state.showCourse && this.props.role === 'student') {

            show = (<Grid item> <CourseTeacherInfo userID={this.props.userID} course={this.state.activeCourse} obligations={this.state.obligations} ></CourseTeacherInfo>  </Grid>)
            //show = (<div> <CourseInfo userID={this.props.userID} course={this.state.activeCourse} />  </div>)

        }
        else if (this.state.showCourse && this.props.role === 'teacher') {
            show = (<Grid item> <CourseTeacherInfo userID={this.props.userID} course={this.state.activeCourse} obligations={this.state.obligations} ></CourseTeacherInfo>  </Grid>)
        }

        if (this.state.showEnrolForm) {
            showEnrol = (<Grid item>
                <Card>
                    <ThemeProvider theme={theme}>
                        <Button onClick={this.enrol}> Prijavi se na kurs </Button>
                    </ThemeProvider>
                    <Dialog
                        open={this.state.open}
                        TransitionComponent={this.Transition}
                        keepMounted
                        onClose={this.handleClose}
                        aria-labelledby="alert-dialog-slide-title"
                        aria-describedby="alert-dialog-slide-description"
                    >
                        <DialogTitle id="alert-dialog-slide-title">{"Obrada zahteva..."}</DialogTitle>
                        <DialogContent>
                            <DialogContentText id="alert-dialog-slide-description">
                                Vas zahtev se obradjuje. Molimo vas da probate opet za par trenutaka.
                            </DialogContentText>
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={this.handleClose} color="primary">
                                Izadji
          </Button>
                        </DialogActions>
                    </Dialog>
                </Card>
            </Grid>)
        }

        return <React.Fragment >
            <CssBaseline />
            <Grid container spacing={3} direction="column" alignItems="stretch">
                {this.state.courses.map((course) => {
                    if (course === this.state.activeCourse)
                        return (<Grid item>
                            <CourseItem role={this.props.role} key={course.courseID} course={course} clicked={this.eventHandler} /> {show}
                            {showEnrol} </Grid>)
                    else return (<Grid item> <CourseItem role={this.props.role} key={course.courseID} course={course} clicked={this.eventHandler} /> </Grid>)
                })}

            </Grid>
        </React.Fragment >

    }
}

//PropTypes
Courses.propTypes = {
    courses: PropTypes.array.isRequired
}

export default Courses;