import React, { Component } from 'react'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import TextField from '@material-ui/core/TextField'
import Card from '@material-ui/core/Card'
import Typography from '@material-ui/core/Typography'
import Avatar from '@material-ui/core/Avatar'
import axios from 'axios'
import Select from '@material-ui/core/Select'
import MenuItem from '@material-ui/core/MenuItem'
import InputLabel from '@material-ui/core/InputLabel'

export default class AddObligation extends Component {

    state = {
        date: {},
        description: {},
        type: {},
        courses: [],
        course: {}

    }

    addObligation = async (state) => {
        try {
            console.log(state)
            const toSend = {
                date: state.date,
                description: state.description,
                type: state.type
            }
            let response =
                await axios.post('http://localhost:8100/course-microservice/courses/' + this.state.course + '/obligations',
                    toSend)
            console.log(response.data)
        }
        catch (err) {
            console.log(err)
        }
    }

    add = () => {
        const obligationToSend = {
            'date': this.state.date,
            'description': this.state.description,
            'type': this.state.type
        }

        console.log(obligationToSend)

        this.addObligation(obligationToSend)
    }


    async componentWillMount() {
        console.log("pre svega iz add obligation")

        if (this.props.role === 'teacher') {
            try {
                console.log('1.')
                let response = await axios.get('http://localhost:8100/course-microservice/teachers/' + this.props.userID);
                console.log(response)
                this.setState({
                    courses: response.data
                })

            }
            catch (err) {
                console.log(err)
            }
        }

    }

    changeDate = (ev) => {
        this.setState({ date: ev.target.value })
    }

    changeDescription = (ev) => {
        this.setState({ description: ev.target.value })
    }

    changeType = (ev) => {
        this.setState({ type: ev.target.value })
    }

    changeCourse = (ev) => {
        this.setState({ course: ev.target.value })
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
                                    <Grid spacing={5} container direction="column" justify='space-evenly' alignItems="stretch">
                                        <Grid item md={2}>  <Typography variant="h6"> Datum </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> Opis </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> Tip </Typography> </Grid>
                                        <Grid item > <Typography variant="h6"> Kurs </Typography> </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item>
                                    <Grid container spacing={2} direction="column" justify="space-evenly" alignItems="center">
                                        <Grid item> <TextField
                                            onChange={this.changeDate}
                                            id="datetime-local"
                                            label="Pick a date"
                                            type="datetime-local"
                                            defaultValue="2020-04-04T10:30"
                                            InputLabelProps={{
                                                shrink: true,
                                            }}
                                        /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeDescription} variant="filled" /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeType} variant="filled" /></Grid>
                                        <InputLabel id="demo-simple-select-helper-label">Izaberi kurs</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-helper-label"
                                            id="demo-simple-select"
                                            onChange={this.changeCourse}>
                                            {this.state.courses.map((course) => (<MenuItem value={course.courseID}> {course.name} </MenuItem>))}
                                        </Select>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid item>
                            <Grid container direction="row" justify='center'>
                                <Grid item>
                                    <Button onClick={this.add} variant="contained" size="large" color="primary"> Kreiraj obavezu </Button>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Card>
            </React.Fragment>
        )
    }
}
