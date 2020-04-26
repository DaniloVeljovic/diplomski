import React, { Component } from 'react'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import TextField from '@material-ui/core/TextField'
import Card from '@material-ui/core/Card'
import Typography from '@material-ui/core/Typography'
import Avatar from '@material-ui/core/Avatar'
import axios from 'axios'


export default class AddCourse extends Component {

    state = {
        name: {},
        description: {},
        semester: {},

    }

    changeName = (ev) => {
        this.setState({ name: ev.target.value })
    }

    changeSemester = (ev) => {
        this.setState({ semester: ev.target.value })
    }

    changeRole = (ev) => {
        this.setState({ role: ev.target.value })
    }

    changeDescription = (ev) => {
        this.setState({ description: ev.target.value })
    }

    addCourse = async (state) => {
        try {
            console.log(state)
            let response = await axios.post('http://localhost:8100/course-microservice/courses/teachers/' + this.props.userID, state)
            this.setState({
                teacherCourses: response.data
            })
        }
        catch (err) {
            console.log(err)
        }
    }

    change = () => {
        const stateToSend = {
            'name': this.state.name,
            'description': this.state.description,
            'semester': this.state.semester
        }
        console.log(stateToSend)
        this.addCourse(stateToSend);
    }



    render() {
        return (
            <React.Fragment>
                <Card>
                    <Grid container direction="column" justify='center' spacing={2} alignItems="stretch">
                        <Grid item> <Grid container direction="row" justify='center'> <Grid item><Avatar variant="square" >A</Avatar></Grid> </Grid> </Grid>
                        <Grid item>
                            <Grid container direction="row" justify='space-evenly' spacing={1}>
                                <Grid item>
                                    <Grid spacing={3} container direction="column" justify='space-evenly' alignItems="stretch">
                                        <Grid item md={2}>  <Typography variant="h6"> Ime kursa </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> Opis </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> Semester </Typography> </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item>
                                    <Grid container spacing={2} direction="column" justify="space-evenly" alignItems="center">
                                        <Grid item> <TextField multiline={false} onChange={this.changeName} variant="filled" /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeDescription} variant="filled" /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeSemester} variant="filled" /></Grid>
                                    </Grid>
                                </Grid>

                            </Grid>
                        </Grid>
                        <Grid item>
                            <Grid container direction="row" justify='center'>
                                <Grid item>
                                    <Button onClick={this.change} variant="contained" size="large" color="primary"> Kreiraj kurs </Button>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Card>
            </React.Fragment>
        )
    }
}
