import React, { Component } from 'react';
import ObligationItem from './ObligationItem';
import PropTypes from 'prop-types';
import Grid from '@material-ui/core/Grid'
import TextField from '@material-ui/core/TextField'
import CssBaseline from '@material-ui/core/CssBaseline';
import Button from '@material-ui/core/Button'
import { createMuiTheme, ThemeProvider } from '@material-ui/core/styles';
import axios from 'axios'


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
                margin: '0 0 0 430px',
                boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
            },
        },
    },
});

class Obligations extends Component {

    state = {
        date: {},
        obligations: []
    }



    onSubmit = async (e) => {
        e.preventDefault();

        console.log(this.props.userID)
        console.log(e)

        if (this.props.role === 'teacher') {
            try {
                let res = await axios.get('http://localhost:8100/course-microservice/teachers/' + this.props.userID + '/obligations/' + this.state.date)

                console.log("Sve obligacije za teachera ")
                console.log(res.data)
                this.setState({ obligations: res.data })
            }
            catch (err) {
                console.log(err)
            }
        }
        else {
            try {
                let res = await axios.get('http://localhost:8100/course-microservice/students/' + this.props.userID + '/date/' + this.state.date)

                console.log("Sve obligacije za studenta ")
                console.log(res.data)
                this.setState({ obligations: res.data })
            }
            catch (err) {
                console.log(err)
            }
        }


    }

    changeDate = (e) => {
        console.log(this.props.role)
        console.log("Old State: " + this.state.date)
        console.log(e.target.value)
        this.setState({ date: e.target.value }, () => {
            console.log("new state: " + this.state.date)
        })
    }

    render() {
        return <Grid item sm >
            <CssBaseline />
            <TextField
                onChange={this.changeDate}
                id="datetime-local"
                label="Izaberi datum:"
                type="datetime-local"
                defaultValue="2020-04-05T10:30:00"
                variant="filled"
                InputLabelProps={{
                    shrink: true,
                }}
            />
            <ThemeProvider theme={theme}>
                <Button onClick={this.onSubmit}> Potrazi obaveze! </Button>
            </ThemeProvider>
            <Grid container spacing={2} justify="space-evenly" direction="column">

                {this.state.obligations.map((oblig) => (
                    <Grid item> <ObligationItem key={oblig.id} obligat={oblig} /> </Grid>))}

            </Grid>
        </Grid>


    }
}

//PropTypes
Obligations.propTypes = {
    obligations: PropTypes.array.isRequired
}

export default Obligations;